package com.zheleznyakov.lingvo.basic.exercisers.mainform;

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary;
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig.Mode;
import com.zheleznyakov.lingvo.basic.dictionary.Record;
import com.zheleznyakov.lingvo.basic.exercisers.ExerciseException;
import com.zheleznyakov.lingvo.basic.exercisers.WordExerciser;

public class MainFormExerciser extends WordExerciser<MainFormExercise, MainFormAnswer> {
    private Mode mode;
    private Record exercisedRecord;
    private boolean isInForwardMode;

    public MainFormExerciser(LearningDictionary dictionary) {
        super(dictionary);
    }

    @Override
    public void start() throws ExerciseException {
        super.start();
        mode = dictionary.getConfig().getMode();
        isInForwardMode = mode != Mode.BACKWARD;
    }

    @Override
    protected MainFormExercise getNextExercise(Record record) {
        exercisedRecord = record;
        return new MainFormExercise(record, isInForwardMode);
    }

    @Override
    protected void doSubmitAnswer(MainFormAnswer answer) {
        if (answerIsCorrect(answer))
            dictionary.registerCorrectAnswer(exercisedRecord);
        else
            dictionary.registerIncorrectAnswer(exercisedRecord);
        setNextMode();
    }

    private boolean answerIsCorrect(MainFormAnswer answer) {
        String stringAnswer = answer.answer;
        return isInForwardMode
                ? exercisedRecord.description.equals(stringAnswer)
                : exercisedRecord.word.getMainForm().equals(stringAnswer);
    }

    private void setNextMode() {
        if (mode == Mode.TOGGLE)
            isInForwardMode = !isInForwardMode;

    }

    public Mode getMode() {
        return mode;
    }
}
