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
    private Iterator<Word> wordIterator;
    private Word exercisedWord;

    public WordTester(LearningDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void start() {
        Util.validateState(wordIterator == null || !wordIterator.hasNext(), "Exerciser is already initialised");
        List<Word> words  = new ArrayList<>(dictionary.getWords());
        Collections.shuffle(words);
        this.wordIterator = words.iterator();
    }

    public boolean hasNext() {
        return wordIterator.hasNext();
    }

    public Word getNextWord() {
        exercisedWord = wordIterator.next();
        return exercisedWord;
    }

    public void test(String answer) {
        boolean answerIsCorrect = checkAnswer(answer);
        dictionary.registerAttempt(exercisedWord, answerIsCorrect);
    }

    private boolean checkAnswer(String answer) {
        if (mode == Mode.FORWARD)
            return dictionary.getMeaning(exercisedWord).equals(answer);
        else
            return exercisedWord.getMainForm().equals(answer);
    }

    public enum Mode {
        FORWARD,
        BACKWARD,
//        TOGGLE;
    }

}
