package com.zheleznyakov.lingvo.basic.exercisers.mainform;

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary;
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig.Mode;
import com.zheleznyakov.lingvo.basic.dictionary.Record;
import com.zheleznyakov.lingvo.basic.exercisers.ExerciseException;
import com.zheleznyakov.lingvo.basic.exercisers.WordExerciser;
import com.zheleznyakov.lingvo.util.Util;

import java.util.Arrays;

public class MainFormExerciser extends WordExerciser<MainFormExercise, MainFormAnswer> {
    private Mode mode;
    private Record exercisedRecord;
    private boolean isInForwardMode;
    private boolean strict;

    public MainFormExerciser(LearningDictionary dictionary) {
        super(dictionary);
    }

    @Override
    public void start() throws ExerciseException {
        super.start();
        mode = dictionary.getConfig().getMode();
        strict = dictionary.getConfig().isStrict();
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
                ? answerIsCorrect(exercisedRecord.description, stringAnswer)
                : answerIsCorrect(exercisedRecord.word.getMainForm(), stringAnswer);
    }

    private boolean answerIsCorrect(String expectedAnswer, String submittedAnswer) {
        if (Util.isBlank(submittedAnswer) || !submittedAnswer.matches(".*\\w+.*"))
            return false;
        if (expectedAnswer.equals(submittedAnswer))
            return true;
        if (strict)
            return false;

        int index = expectedAnswer.indexOf(submittedAnswer);
        return index >= 0 && (index == 0 || expectedAnswer.charAt(index - 1) == ' ');
    }

    private void setNextMode() {
        if (mode == Mode.TOGGLE)
            isInForwardMode = !isInForwardMode;
    }

    public Mode getMode() {
        return mode;
    }
}
