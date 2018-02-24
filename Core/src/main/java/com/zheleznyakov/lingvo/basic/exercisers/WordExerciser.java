package com.zheleznyakov.lingvo.basic.exercisers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary;
import com.zheleznyakov.lingvo.basic.dictionary.Record;

public abstract class WordExerciser<E extends Exercise, A extends Answer> {
    protected final LearningDictionary dictionary;
    private State state = State.INITIALISED;
    private boolean readyForNext = true;

    private Iterator<Record> recordIterator;

    protected WordExerciser(LearningDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public State getState() {
        return state;
    }

    public void start() throws ExerciseException {
        assertCurrentState(State.INITIALISED, "Failed to start");
        ArrayList<Record> records = new ArrayList<>(dictionary.getRecords());
        Collections.shuffle(records);
        recordIterator = records.iterator();
        state = recordIterator.hasNext() ? State.STARTED : State.STOPPED;
    }

    public E next() throws ExerciseException {
        assertCurrentState(State.STARTED, "Failed to start next exercise");
        if (!readyForNext)
            throw new ExerciseException("Cannot go to the next exercise: please submit the answer first");
        readyForNext = false;
        return getNextExercise(recordIterator.next());
    }

    protected abstract E getNextExercise(Record record);

    public void submitAnswer(A answer) throws ExerciseException {
        assertCurrentState(State.STARTED, "Failed to submit the answer");
        if (readyForNext)
            throw new ExerciseException("Cannot submit an answer: please start the new exercise first");
        doSubmitAnswer(answer);
        readyForNext = true;
        if (!hasNext())
            state = State.STOPPED;
    }

    protected abstract void doSubmitAnswer(A answer);

    private void assertCurrentState(State expectedState, String reason) throws ExerciseException {
        if (state != expectedState)
            throw new ExerciseException(reason + ": the exerciser has state [{}], expected [{}]", state, expectedState);
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
