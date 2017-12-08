package com.zheleznyakov.lingvo.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.util.Util;

public class WordTester {
    private final LearningDictionary dictionary;

    private boolean strict = true;
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
        validateTesterNotStarted();
        this.mode = mode;
    }

    public void start() {
        validateTesterNotStarted();
        List<Word> words  = new ArrayList<>(dictionary.getWords());
        Collections.shuffle(words);
        this.wordIterator = words.iterator();
        nextCheckMode = mode == Mode.TOGGLE ? Mode.FORWARD : mode;
    }

    private void validateTesterNotStarted() {
        Util.validateState(wordIterator == null || !wordIterator.hasNext(), "Tester has already been started");
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
        BiPredicate<String, String> answerChecker = getAnswerChecker();
        return nextCheckMode == Mode.FORWARD
                ? answerChecker.test(dictionary.getMeaning(exercisedWord), answer)
                : answerChecker.test(exercisedWord.getMainForm(), answer);
    }

    private void toggleNextCheckMode() {
        nextCheckMode = nextCheckMode == Mode.FORWARD ? Mode.BACKWARD : Mode.FORWARD;
    }

    private BiPredicate<String, String> getAnswerChecker() {
        return strict ? String::equals : String::startsWith;
    }

    public void setStrict(boolean strict) {
        validateTesterNotStarted();
        this.strict = strict;
    }

    public enum Mode {
        FORWARD,
        BACKWARD,
        TOGGLE
    }

}
