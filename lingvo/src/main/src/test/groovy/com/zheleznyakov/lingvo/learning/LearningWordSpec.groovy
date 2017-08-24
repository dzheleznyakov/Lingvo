package com.zheleznyakov.lingvo.learning

import com.zheleznyakov.lingvo.basic.Word
import com.zheleznyakov.lingvo.language.Language
import com.zheleznyakov.lingvo.language.en.word.EnNoun
import spock.lang.Specification
import spock.lang.Unroll

class LearningWordSpec extends Specification {
    LearningDictionary dictionary
    Word word
    String meaning

    void setup() {
        dictionary = new LearningDictionary(Language.ENGLISH)
        word = EnNoun.build("word")
        meaning = "слово"
    }

    @Unroll
    def "Test learning word; pattern -- +#successfulStreak -#failingStreak"() {
        given: "a word and a dictionary"
        dictionary.add(word, meaning)

        and: "a Word Exerciser"
        WordExerciser exerciser = new WordExerciser(dictionary)

        when: "the dictionary is exercised according to pattern"
        exerciseWord(exerciser, successfulStreak, true)
        exerciseWord(exerciser, failingStreak, false)

        then: "the statistics has correct values"
        dictionary.getStatistics(word).count == expectedCount

        where: "the exercise patterns are"
        successfulStreak | failingStreak || expectedCount
        0                | 0             || 0
        10               | 0             || 10
        5                | 1             || 5
        5                | 2             || 4
        5                | 3             || 3
        0                | 10            || 0
    }

//    def "Check whether the word is learned in the MEANING category"() {
//        given: "that the dictionary is configured"
//        int maxLearningCount = 10
//        dictionary.maxLearningCount = maxLearningCount
//
//        when: "the word is added to the dictionary"
//        dictionary.add(word, meaning)
//
//        and: "the word is exercised successfully enough times"
//        exerciseWord(maxLearningCount, true)
//
//        then: "the word is learned"
//        dictionary.isLearned(word, MeaningExercise.FORWARD)
//    }

    def exerciseWord(WordExerciser exerciser, int numberOfRounds, boolean successfully) {
        String answer = meaning + (successfully ? "" : "я")
        for (int i = 0; i < numberOfRounds; i++)
            exerciser.exercise(word, answer)
    }

}
