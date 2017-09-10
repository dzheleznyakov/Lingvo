package com.zheleznyakov.lingvo.learning

import com.zheleznyakov.lingvo.basic.Word
import com.zheleznyakov.lingvo.language.Language
import com.zheleznyakov.lingvo.language.en.word.EnNoun
import com.zheleznyakov.lingvo.learning.WordExerciser.Mode
import spock.lang.Specification
import spock.lang.Unroll

import static com.zheleznyakov.lingvo.learning.WordExerciser.Mode.BACKWARD
import static com.zheleznyakov.lingvo.learning.WordExerciser.Mode.FORWARD

class LearningWordSpec extends Specification {
    LearningDictionary dictionary
    Word word
    String meaning
    WordExerciser exerciser

    void setup() {
        dictionary = new LearningDictionary(Language.ENGLISH)
        word = EnNoun.build("word")
        meaning = "слово"
        dictionary.add(word, meaning)
        exerciser = new WordExerciser(dictionary)
    }

    @Unroll
    def "Test learning word in #mode mode; pattern -- +#successfulStreak -#failingStreak"() {
        given: "a Word Exerciser in a mode"
        exerciser.mode = mode

        when: "the dictionary is exercised according to pattern"
        exerciseWord(exerciser, mode, successfulStreak, true)
        exerciseWord(exerciser, mode, failingStreak, false)

        then: "the statistics has correct values"
        dictionary.getStatistics(word).count == expectedCount

        where: "the exercise patterns are"
        mode     | successfulStreak | failingStreak || expectedCount
        FORWARD  | 0                | 0             || 0
        FORWARD  | 10               | 0             || 10
        FORWARD  | 5                | 1             || 5
        FORWARD  | 5                | 2             || 4
        FORWARD  | 5                | 3             || 3
        FORWARD  | 0                | 10            || 0
        BACKWARD | 0                | 0             || 0
        BACKWARD | 10               | 0             || 10
        BACKWARD | 5                | 1             || 5
        BACKWARD | 5                | 2             || 4
        BACKWARD | 5                | 3             || 3
        BACKWARD | 0                | 10            || 0
    }

//    def "When trying to learn a word in toggle mode, throw"() {
//        given: "a Word Exerciser in TOGGLE mode"
//        exerciser.mode = TOGGLE
//
//        when: "trying to exercise the word"
//        exerciser.exercise(word, "any string")
//
//        then: "an IllegalArgumentException is thrown"
//        IllegalArgumentException e = thrown();
//        e.getMessage().contains("TOGGLE regime cannot be used to exercise a single word")
//    }

    def exerciseWord(WordExerciser exerciser, Mode mode, int numberOfRounds, boolean successfully) {
        String answer
        switch (mode) {
            case FORWARD : answer = meaning + (successfully ? "" : "я"); break
            case BACKWARD: answer = word.mainForm + (successfully ? "" : "e"); break
        }
        for (int i = 0; i < numberOfRounds; i++)
            exerciser.exercise(word, answer)
    }

}
