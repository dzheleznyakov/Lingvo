package com.zheleznyakov.lingvo.basic.exercisers.mainform

import com.google.common.collect.ImmutableList
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig
import com.zheleznyakov.lingvo.implementations.FakeEnglish
import com.zheleznyakov.lingvo.implementations.TestableMultiFormNoun
import com.zheleznyakov.lingvo.helpers.TestHelper
import spock.lang.Specification
import spock.lang.Unroll

import static com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig.Mode.*

class MainFormExerciserSpec extends Specification {
    private LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE, "Test dictionary"]

    def setup() {
        dictionary.config.maxLearnCount = 10
        dictionary.config.strict = true
        TestHelper.addFullRecordsToDictionary(dictionary, 10)
    }

    def "When a main form exerciser is created, its config equals to the one of dictionary"() {
        expect: "that the dictionary does not have a default config"
        TestHelper.areConfigsNotEqual(dictionary.config, LearningDictionaryConfig.default)

        when: "a new main form exerciser is created and started"
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()

        then: "its mode is as in the dictionary's config"
        exerciser.mode == dictionary.config.mode
    }

    @Unroll
    def "Test one word exercise in #mode mode"() {
        given: "the dictionary has one record"
        dictionary = [FakeEnglish.FIXED_LANGUAGE, "Test dictionary"]
        def mainForm = "word"
        def description = "description"
        dictionary.record([mainForm] as TestableMultiFormNoun, description).add()

        and: "that the dictionary is in #mode mode"
        dictionary.getConfig().setMode(mode)

        and: "a started exerciser"
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()

        when: "the word is exercised"
        exerciser.next()
        exerciser.submitAnswer([answer] as MainFormAnswer)

        then:
        allRecordsHaveCount(1)

        where: "the parameters are"
        mode     | answer
        FORWARD  | "description"
        BACKWARD | "word"
    }

    def "Test one word exercise in TOGGLE mode"() {
        given: "the dictionary has four words"
        dictionary = [FakeEnglish.FIXED_LANGUAGE, "Test dictionary"]
        TestHelper.addFullRecordsToDictionary(dictionary, 4)

        and: "is in TOGGLE mode"
        dictionary.getConfig().setMode(TOGGLE)

        and: "a started exerciser"
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()

        when: "the words are exercised toggling modes (FORWARD/BACKWARD)"
        def exercise = exerciser.next()
        exerciser.submitAnswer(getCorrectAnswerInForwardMode(exercise))

        exercise = exerciser.next()
        exerciser.submitAnswer(getCorrectAnswerInBackwardMode(exercise))

        exercise = exerciser.next()
        exerciser.submitAnswer(getCorrectAnswerInForwardMode(exercise))

        exercise = exerciser.next()
        exerciser.submitAnswer(getCorrectAnswerInBackwardMode(exercise))

        then: "all records have count 1"
        allRecordsHaveCount(1)
    }

    @Unroll
    def "Test Exercise for main form exerciser in #mode mode"() {
        given: "a dictionary with one record in #mode mode"
        dictionary = [FakeEnglish.FIXED_LANGUAGE, "Test dictionary"]
        dictionary.getConfig().setMode(mode)
        TestHelper.addFullRecordsToDictionary(dictionary, 1)

        and: "a started main form exerciser"
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()

        when: "the next exercise is returned"
        def exercise = exerciser.next()
        def record = dictionary.records.iterator().next()

        then: "the exercise is as expected"
        with(exercise) {
            mainForm == expectedMainForm.call(record)
            partOfSpeech == expectedPartOfSpeech.call(record)
            description == expectedDescription.call(record)
            transcription == expectedTranscription.call(record)
            usageExamples == record.examples.stream()
                    .map(exampleMapper)
                    .collect(ImmutableList.toImmutableList())
        }

        where: "the parameters are"
        mode     || expectedMainForm     | expectedPartOfSpeech           | expectedDescription | expectedTranscription | exampleMapper
        FORWARD  || { it.word.mainForm } | { it.word.partOfSpeech.brief } | { null }            | { it.transcription }  | { it.example }
        BACKWARD || { null }             | { null }                       | { it.description }  | { null }              | { it.translation }
    }

    def "Records in a newly created dictionary have record count 0"() {
        expect: "the learning count of all records to be 0"
        allRecordsHaveCount(0)
    }

    def "When a word is exercised correctly, its learn count increases by one"() {
        when: "exercising main forms of all words correctly"
        exerciseWordsMainFormInForwardMode(true)

        then: "all records have count 1"
        allRecordsHaveCount(1)
    }

    def "When a word is exercised incorrectly one time, the count remains the same"() {
        given: "that the records in the dictionary have learn count 1"
        exerciseWordsMainFormInForwardMode(true)

        when: "exercising main forms of all words incorrectly"
        exerciseWordsMainFormInForwardMode(false)

        then: "all records still have count 1"
        allRecordsHaveCount(1)
    }

    def "When a word is exercised incorrectly two times in a row, the count goes down by 1"() {
        given: "that the records in the dictionary have learn count 1"
        exerciseWordsMainFormInForwardMode(true)

        when: "exercising main form of all words incorrectly twice in a row"
        exerciseWordsMainFormInForwardMode(false)
        exerciseWordsMainFormInForwardMode(false)

        then: "all records have count 0"
        allRecordsHaveCount(0)
    }

    def "Word learn count does not go below 0"() {
        when: "exercising main form of all words incorrectly twice in a row"
        exerciseWordsMainFormInForwardMode(false)
        exerciseWordsMainFormInForwardMode(false)

        then: "all records have count 0"
        allRecordsHaveCount(0)
    }

    def "When exercising words incorrectly two times not in a row, then learn count does not go down"() {
        when: "exercising main form of all words correctly/incorrectly/correctly/incorrectly"
        exerciseWordsMainFormInForwardMode(true)
        exerciseWordsMainFormInForwardMode(false)
        exerciseWordsMainFormInForwardMode(true)
        exerciseWordsMainFormInForwardMode(false)

        then: "all records have count 2"
        allRecordsHaveCount(2)
    }

    def "Records that hit max learn count are not exercised"() {
        given: "that dictionary has one record"
        def maxLearnCount = 1;
        dictionary = [FakeEnglish.FIXED_LANGUAGE, "Test dictionary"]
        dictionary.getConfig().setMaxLearnCount(maxLearnCount)
        dictionary.record(["word"] as TestableMultiFormNoun, "description").add()
        exerciseWordsMainFormInForwardMode(true)

        expect: "that the record hit max learn count"
        dictionary.records.forEach { assert dictionary.getLearnCount(it) == maxLearnCount }

        when: "more records added to the dictionary"
        def numberOfNewWords = 10
        TestHelper.addFullRecordsToDictionary(dictionary, numberOfNewWords)

        and: "the main forms are exercised"
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()
        def exercisedWords = new HashSet()
        while (exerciser.hasNext()) {
            exercisedWords.add exerciser.next().mainForm
            exerciser.submitAnswer(["answer"] as MainFormAnswer)
        }

        then: "the very first record is not exercised"
        dictionary.records.size() == numberOfNewWords + 1
        exercisedWords.size() == numberOfNewWords
        !exercisedWords.contains("word")
    }

    @Unroll
    def "Test exercising in strict=#strict regime: description=[#description], answer=[#answer]"() {
        given: "a dictionary"
        dictionary = [FakeEnglish.FIXED_LANGUAGE, "Test dictionary"]
        dictionary.record(["word"] as TestableMultiFormNoun, description).add()

        and: "that the dictionary is in strict=#strict regime"
        dictionary.getConfig().setStrict(strict)

        and: "a started exerciser"
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()

        when: "the answer is submitted"
        exerciser.next()
        exerciser.submitAnswer([answer] as MainFormAnswer)

        then: "the recird learn count is as expected"
        allRecordsHaveCount(expectedLearnCount)

        where: "the parameters are"
        strict | description | answer || expectedLearnCount
        false  | "abc"       | "ab"   || 1
        true   | "abc"       | "ab"   || 0
        false  | "abc"       | "abc"  || 1
        true   | "abc"       | "abc"  || 1
        false  | "abc"       | "abcd" || 0
        true   | "abc"       | "abcd" || 0
        false  | "abc, def"  | "def"  || 1
        false  | "abc, def"  | "de"   || 1
        false  | "abc (def)" | "de"   || 0
        false  | "abc (def)" | "def)" || 0
        false  | "abc-def"   | "def"  || 0
        false  | "abc - def" | "def"  || 1
        false  | "abc - def" | "-"    || 0
        false  | "abc, def"  | ""     || 0
        true   | "abc, def"  | ""     || 0
        false  | "abc"       | null   || 0
        true   | "abc"       | null   || 0
    }

    private def allRecordsHaveCount(int expectedCount) {
        dictionary.records.stream()
                .forEach { assert dictionary.getLearnCount(it) == expectedCount }
        true
    }

    private def exerciseWordsMainFormInForwardMode(boolean correctly) {
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()
        while (exerciser.hasNext()) {
            def exercise = exerciser.next()
            def answer = getAnswerInForwardMode(exercise, correctly)
            exerciser.submitAnswer(answer)
        }
    }

    private MainFormAnswer getAnswerInForwardMode(MainFormExercise mainFormExercise, boolean correctly) {
        def mainForm = mainFormExercise.mainForm
        def description = dictionary.records.find { it.word.mainForm == mainForm }.description
        [description + (correctly ? "" : " (incorrect)")] as MainFormAnswer
    }

    private MainFormAnswer getCorrectAnswerInForwardMode(MainFormExercise exercise) {
        getAnswerInForwardMode(exercise, true)
    }

    private MainFormAnswer getCorrectAnswerInBackwardMode(MainFormExercise exercise) {
        def description = exercise.description
        def mainForm = dictionary.records.find { it.description == description }.word.mainForm
        [mainForm] as MainFormAnswer
    }
}