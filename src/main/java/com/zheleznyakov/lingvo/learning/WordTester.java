package com.zheleznyakov.lingvo.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.util.Util;

public class WordTester {
    private final LearningDictionary dictionary;
    private Mode mode = Mode.FORWARD;
    private Mode nextCheckMode;
    private Iterator<Word> wordIterator;
    private Word exercisedWord;

    public WordTester(LearningDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void start() {
        Util.validateState(wordIterator == null || !wordIterator.hasNext(), "Tester has already been started");
        List<Word> words  = new ArrayList<>(dictionary.getWords());
        Collections.shuffle(words);
        this.wordIterator = words.iterator();
        nextCheckMode = mode == Mode.TOGGLE ? Mode.FORWARD : mode;
    }

    public boolean hasNext() {
        return wordIterator.hasNext();
    }

    public Word getNextWord() {
        if (!wordIterator.hasNext())
            return null;
        exercisedWord = wordIterator.next();
        return dictionary.isLearned(exercisedWord) ? getNextWord() : exercisedWord;
    }

    public void test(String answer) {
        boolean answerIsCorrect = checkAnswer(answer);
        dictionary.registerAttempt(exercisedWord, answerIsCorrect);
        if (mode == Mode.TOGGLE)
            toggleNextCheckMode();
    }

    private boolean checkAnswer(String answer) {
        if (nextCheckMode == Mode.FORWARD)
            return dictionary.getMeaning(exercisedWord).equals(answer);
        else
            return exercisedWord.getMainForm().equals(answer);
    }

    private void toggleNextCheckMode() {
        nextCheckMode = nextCheckMode == Mode.FORWARD ? Mode.BACKWARD : Mode.FORWARD;
    }

    public enum Mode {
        FORWARD,
        BACKWARD,
        TOGGLE
    }

}
