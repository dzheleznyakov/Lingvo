package com.zheleznyakov.lingvo.learning.checker;

import java.util.function.Consumer;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.learning.LearningDictionary;

public class MainFormChecker {
    private final LearningDictionary dictionary;

    public MainFormChecker(LearningDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public boolean exercise(Word word, String meaning) {
        String meaningInDictionary = dictionary.getMeaning(word);
        return validateAndRegisterAnswer(word, meaning, meaningInDictionary);
    }

    private boolean validateAndRegisterAnswer(Word word, String meaning, String meaningInDictionary) {
        boolean answerIsCorrect = meaningInDictionary.equals(meaning);
        Consumer<Word> registrar = answerIsCorrect
                ? dictionary::registerCorrectAnswer
                : dictionary::registerIncorrectAnswer;
        registrar.accept(word);
        return answerIsCorrect;
    }

    public void exercisePartialAnswer(Word word, String meaning) {
        String meaningInDictionary = dictionary.getMeaning(word);
//        validateAndRegisterAnswer(word, );
    }
}
