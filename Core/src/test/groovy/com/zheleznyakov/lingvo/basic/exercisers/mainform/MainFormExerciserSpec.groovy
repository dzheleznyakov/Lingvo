package com.zheleznyakov.lingvo.basic.exercisers.mainform

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig
import com.zheleznyakov.lingvo.basic.exercisers.Answer
import com.zheleznyakov.lingvo.basic.implementations.FakeEnglish
import com.zheleznyakov.lingvo.basic.implementations.TestableMultiFormWord
import com.zheleznyakov.lingvo.helpers.LearningDictionaryConfigHelper
import spock.lang.Specification

import java.util.stream.IntStream

class MainFormExerciserSpec extends Specification {
    private final LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE]

    def setup() {
        dictionary.config.maxLearnCount = 10
        IntStream.range(0, 10)
                .mapToObj { (it + (char)'a') as char }
                .map { ["word${it}"] as TestableMultiFormWord }
                .forEach { dictionary.record(it, "${it.mainForm} description").add() }
        assert dictionary.records.size() == 10
    }

    /*
   - Learn in different modes (forward, backward, toggle)
   - Learn only those words, that are not completed
   - Check statistics (percentage)
   - Configure (set learned count)
    */

    def "When a main form exerciser is created, its config equals to the one of dictionary"() {
        expect: "that the dictionary does not have a default config"
        LearningDictionaryConfigHelper.areConfigsNotEqual(dictionary.config, LearningDictionaryConfig.default)

        when: "a new main form exerciser is created and started"
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()

        then: "its mode is as in the dictionary's config"
        exerciser.mode == dictionary.config.mode
    }

    def "Test Exercise for main form exerciser"() {

    }

    def "Records in a newly created dictionary have record count 0"() {
        expect: "the learning count of all records to be 0"
        allRecordsHaveCount(0)
    }

    def "When a word is exercised correctly, its learn count increases by one"() {
        when: "exercising main forms of all words correctly"
        exerciseWordsMainFormCorrectly()

        then:
        allRecordsHaveCount(1)
    }

    private def allRecordsHaveCount(int expectedCount) {
        dictionary.records.stream()
                .forEach { assert dictionary.getLearnCount(it) == expectedCount }
        true
    }

    def exerciseWordsMainFormCorrectly() {
        MainFormExerciser exerciser = [dictionary]
        exerciser.start()
        while (exerciser.hasNext()) {
            exerciser.next()
            exerciser.submitAnswer(Stub(Answer))
        }
    }

}