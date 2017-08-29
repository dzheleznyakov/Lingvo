package com.zheleznyakov.lingvo.learning;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.util.Precondition;

public class WordExerciser {
    private final LearningDictionary dictionary;
    private Mode mode = Mode.FORWARD;

    public WordExerciser(LearningDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void exercise(Word word, String answer) {
        Precondition.validateArgument(mode != Mode.TOGGLE, "{} regime cannot be used to exercise a single word", mode);
        boolean answerIsCorrect = checkAnswer(word, answer);
        dictionary.registerAttempt(word, answerIsCorrect);
    }

    private boolean checkAnswer(Word word, String answer) {
        switch (mode) {
            case FORWARD : return dictionary.getMeaning(word).equals(answer);
            case BACKWARD: return word.getMainForm().equals(answer);
            default: throw new IllegalArgumentException(mode + " is unknown");
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public enum Mode {
        FORWARD,
        BACKWARD,
        TOGGLE;
    }

}
