package com.zheleznyakov.lingvo.basic.dictionary;

import java.util.Iterator;

import com.zheleznyakov.lingvo.basic.words.GrammaticalWord;

public class WordLearner {
    private final LearningDictionary dictionary;
    private State state = State.INITIALISED;

    private Iterator<Record> recordIterator;

    public WordLearner(LearningDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public State getState() {
        return state;
    }

    public void start() {
        recordIterator = dictionary.getRecords().iterator();
        state = recordIterator.hasNext() ? State.STARTED : State.STOPPED;
    }

    public GrammaticalWord next() throws ExerciseException {
        if (state != State.STARTED)
            throw new ExerciseException("Failed to exercise: the learner has state [{}], expected [{}]", state, State.STARTED);
        return recordIterator.next().word;
    }

    public void submitAnswer(String answer) {
        if (!hasNext())
            state = State.STOPPED;
    }

    public boolean hasNext() {
        return recordIterator == null || recordIterator.hasNext();
    }

    public enum State {
        INITIALISED,
        STARTED,
        STOPPED
    }

}
