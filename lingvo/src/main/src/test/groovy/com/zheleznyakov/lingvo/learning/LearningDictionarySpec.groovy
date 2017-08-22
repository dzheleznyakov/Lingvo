package com.zheleznyakov.lingvo.learning

import com.zheleznyakov.lingvo.basic.Word
import com.zheleznyakov.lingvo.language.Language
import com.zheleznyakov.lingvo.language.en.word.EnNoun
import spock.lang.Specification
import spock.lang.Unroll

class LearningDictionarySpec extends Specification {
    LearningDictionary dictionary
    Word word
    String meaning

    void setup() {
        dictionary = new LearningDictionary(Language.ENGLISH)
        word = EnNoun.build("word")
        meaning = "слово"
    }

    def "When a word is added to the dictionary, its statistics has initial values"() {
        when: "the word is added to the learning dictionary"
        dictionary.add(word, meaning)

        and: "statistics of the word is got"
        def statistics = dictionary.getStatistics(word)

        then: "the statistics has initial values in the Main Form category"
        statistics.getCount(MeaningCategory.MEANING) == 0
    }

    @Unroll
    def "Test word learning with different setback patterns: +#successfulStreak1 -#failingStreak1 +#successfulStreak2"() {
        when: "the word is added to the learning dictionary"
        dictionary.add(word, meaning)

        and: "it is exercised according to the pattern"
        exerciseWord(successfulStreak1, true)
        exerciseWord(failingStreak1, false)
        exerciseWord(successfulStreak2, true)

        then: "the statistics has correct values"
        with (dictionary.getStatistics(word)) {
            getCount(MeaningCategory.MEANING) == expectedCount
        }

        where: "the exercise patterns are"
        successfulStreak1 | failingStreak1 | successfulStreak2 || expectedCount
        10                | 0              | 0                 || 10
        5                 | 1              | 5                 || 10
        5                 | 2              | 5                 || 9
        5                 | 3              | 5                 || 8
    }

    def exerciseWord(int numberOfAttempts, boolean successfully) {
        String meaningVersion = meaning + (successfully ? "" : "я")
        for (int i = 0; i < numberOfAttempts; i++)
            dictionary.exercise(word, MeaningCategory.MEANING, meaningVersion)
    }

}
