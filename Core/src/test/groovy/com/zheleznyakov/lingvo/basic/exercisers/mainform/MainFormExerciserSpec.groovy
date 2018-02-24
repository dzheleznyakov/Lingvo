package com.zheleznyakov.lingvo.basic.exercisers.mainform

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig
import com.zheleznyakov.lingvo.basic.dictionary.Record
import com.zheleznyakov.lingvo.basic.implementations.FakeEnglish
import com.zheleznyakov.lingvo.basic.implementations.TestableMultiFormNoun
import com.zheleznyakov.lingvo.helpers.LearningDictionaryConfigHelper
import spock.lang.Specification

import java.util.stream.IntStream

class MainFormExerciserSpec extends Specification {
    private LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE]

    def setup() {
        dictionary.config.maxLearnCount = 10
        addRecordsToDictionary(10)
    }

    def "When a main form exerciser is created, its config equals to the one of dictionary"() {
        expect: "that the dictionary does not have a default config"
        LearningDictionaryConfigHelper.areConfigsNotEqual(dictionary.config, LearningDictionaryConfig.default)

        when: "a new main form exerciser is created and started"
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()

        then: "its mode is as in the dictionary's config"
        exerciser.mode == dictionary.config.mode
    }

    /*
   - Learn in different modes (forward, backward, toggle)
   - Learn only those words, that are not completed
   - Check statistics (percentage)
   - Configure (set learned count)
    */

    def "Test Exercise for main form exerciser in FORWARD mode"() {
        given: "a dictionary with one record"
        dictionary = [FakeEnglish.FIXED_LANGUAGE]
        addRecordsToDictionary(1)

        and: "a started main form exerciser"
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()

        when: "the next exercise is returned"
        def exercise = exerciser.next()
        def record = dictionary.records.iterator().next()

        then:
        with(exercise) {
            mainForm == record.word.getMainForm()
            partOfSpeech == record.word.partOfSpeech.brief
            description == null
            transcription == record.transcription
            usageExamples == record.examples
        }
    }

    def "Records in a newly created dictionary have record count 0"() {
        expect: "the learning count of all records to be 0"
        allRecordsHaveCount(0)
    }

    def "When a word is exercised correctly, its learn count increases by one"() {
        when: "exercising main forms of all words correctly"
        exerciseWordsMainForm(true)

        then: "all records have count 1"
        allRecordsHaveCount(1)
    }

    def "When a word is exercised incorrectly one time, the count remains the same"() {
        given: "that the records in the dictionary have learn count 1"
        exerciseWordsMainForm(true)

        when: "exercising main forms of all words incorrectly"
        exerciseWordsMainForm(false)

        then: "all records still have count 1"
        allRecordsHaveCount(1)
    }

    def "When a word is exercised incorrectly two times in a row, the count goes down by 1"() {
        given: "that the records in the dictionary have learn count 1"
        exerciseWordsMainForm(true)

        when: "exercising main form of all words incorrectly twice in a row"
        exerciseWordsMainForm(false)
        exerciseWordsMainForm(false)

        then: "all records have count 0"
        allRecordsHaveCount(0)
    }

    def "Word learn count does not go below 0"() {
        when: "exercising main form of all words incorrectly twice in a row"
        exerciseWordsMainForm(false)
        exerciseWordsMainForm(false)

        then: "all records have count 0"
        allRecordsHaveCount(0)
    }

    def "When exercising words incorrectly two times not in a row, then learn count does not go down"() {
        when: "exercising main form of all words correctly/incorrectly/correctly/incorrectly"
        exerciseWordsMainForm(true)
        exerciseWordsMainForm(false)
        exerciseWordsMainForm(true)
        exerciseWordsMainForm(false)

        then: "all records have count 2"
        allRecordsHaveCount(2)
    }

    def "Records that hit max learn count are not exercised"() {
        given: "that dictionary has one record"
        def maxLearnCount = 1;
        dictionary = [FakeEnglish.FIXED_LANGUAGE]
        dictionary.getConfig().setMaxLearnCount(maxLearnCount)
        dictionary.record(["word"] as TestableMultiFormNoun, "description").add()
        exerciseWordsMainForm(true)

        expect: "that the record hit max learn count"
        dictionary.records.forEach { assert dictionary.getLearnCount(it) == maxLearnCount }

        when: "more records added to the dictionary"
        def numberOfNewWords = 10
        addRecordsToDictionary(numberOfNewWords)

        and: "the main forms are exercised"
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()
        def exercisedWords = new HashSet()
        while (exerciser.hasNext()) {
            exercisedWords.add exerciser.next().mainForm
            exerciser.submitAnswer(["answer"] as MainFormAnswer)
        }

        then: "the very first record is not exercised"
        exercisedWords.size() == numberOfNewWords
        !exercisedWords.contains("word")
    }

    private void addRecordsToDictionary(int numberOfRecords) {
        IntStream.range(0, numberOfRecords)
                .mapToObj { (it + (char) 'a') as char }
                .map { ["word${it}"] as TestableMultiFormNoun }
                .forEach { addFullRecord(it) }
    }

    private def addFullRecord(def word) {
        def mainForm = word.mainForm
        dictionary.record(word, "${mainForm} description")
                .withTranscription("${mainForm} transcription")
                .withUsageExample(["${mainForm} example", "${mainForm} translation"] as Record.UsageExample)
                .add()
    }

    private def allRecordsHaveCount(int expectedCount) {
        dictionary.records.stream()
                .forEach { assert dictionary.getLearnCount(it) == expectedCount }
        true
    }

    private def exerciseWordsMainForm(boolean correctly) {
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()
        while (exerciser.hasNext()) {
            def exercise = exerciser.next()
            def answer = getAnswer(exercise, correctly)
            exerciser.submitAnswer(answer)
        }
    }

    private MainFormAnswer getAnswer(MainFormExercise mainFormExercise, boolean correctly) {
        def mainForm = mainFormExercise.mainForm
        def description = dictionary.records.find { it.word.mainForm == mainForm }.description
        [description + (correctly ? "" : " (incorrect)")] as MainFormAnswer
    }
}