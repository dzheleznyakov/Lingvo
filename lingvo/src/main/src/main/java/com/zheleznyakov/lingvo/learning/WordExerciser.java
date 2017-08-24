package com.zheleznyakov.lingvo.learning;

import com.zheleznyakov.lingvo.basic.Word;

public class WordExerciser {
    private final LearningDictionary dictionary;

    public WordExerciser(LearningDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void exercise(Word word, String answer) {
        String expectedAnswer = dictionary.getMeaning(word);
        boolean answerIsCorrect = expectedAnswer.equals(answer);
        dictionary.registerAttempt(word, answerIsCorrect);
    }
}
