package com.zheleznyakov.lingvo.learning.checker;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.learning.LearningDictionary;

public class MainFormChecker {
    private final LearningDictionary dictionary;

    public MainFormChecker(LearningDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void exercise(Word word, String meaning) {
        String meaningInDictionary = dictionary.getMeaning(word);
        if (meaningInDictionary.equals(meaning))
            dictionary.registerCorrectAnswer(word);
        else
            dictionary.registerIncorrectAnswer(word);
    }
}
