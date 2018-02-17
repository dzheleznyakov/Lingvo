package com.zheleznyakov.lingvo.basic.dictionary;

import java.util.Iterator;

import com.zheleznyakov.lingvo.basic.words.GrammaticalWord;

public class WordLearner {
    private final LearningDictionary dictionary;
    private State state = State.INITIALISED;
    private boolean readyForNext = true;

    private Iterator<Record> recordIterator;

    public WordLearner(LearningDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public State getState() {
        return state;
    }

    public void start() throws ExerciseException {
        assertCurrentState(State.INITIALISED, "Failed to start");
        recordIterator = dictionary.getRecords().iterator();
        state = recordIterator.hasNext() ? State.STARTED : State.STOPPED;
    }

    public GrammaticalWord next() throws ExerciseException {
        assertCurrentState(State.STARTED, "Failed to start next exercise");
        if (!readyForNext)
            throw new ExerciseException("Cannot go to the next exercise: please submit the answer first");
        readyForNext = false;
        return recordIterator.next().word;
    }

    public void submitAnswer(String answer) throws ExerciseException {
        assertCurrentState(State.STARTED, "Failed to submit the answer");
        if (readyForNext)
            throw new ExerciseException("Cannot submit an answer: please start the new exercise first");
        readyForNext = true;
        if (!hasNext())
            state = State.STOPPED;
    }

    private void assertCurrentState(State expectedState, String reason) throws ExerciseException {
        if (state != expectedState)
            throw new ExerciseException(reason + ": the learner has state [{}], expected [{}]", state, expectedState);
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
