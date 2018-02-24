package com.zheleznyakov.lingvo.basic.exercisers.mainform;

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary;
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig.Mode;
import com.zheleznyakov.lingvo.basic.dictionary.Record;
import com.zheleznyakov.lingvo.basic.exercisers.Answer;
import com.zheleznyakov.lingvo.basic.exercisers.Exercise;
import com.zheleznyakov.lingvo.basic.exercisers.ExerciseException;
import com.zheleznyakov.lingvo.basic.exercisers.WordExerciser;

public class MainFormExerciser extends WordExerciser<Exercise, Answer> {
    private Mode mode;
    private Record exercisedRecord;

    public MainFormExerciser(LearningDictionary dictionary) {
        super(dictionary);
    }

    @Override
    public void start() throws ExerciseException {
        super.start();
        mode = dictionary.getConfig().getMode();
    }

    @Override
    protected Exercise getNextExercise(Record record) {
        exercisedRecord = record;
        return null;
    }

    @Override
    protected void doSubmitAnswer(Answer answer) {
        dictionary.increaseLearnCount(exercisedRecord);
    }

    public Mode getMode() {
        return mode;
    }
}
