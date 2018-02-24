package com.zheleznyakov.lingvo.basic.exercisers.mainform;

import com.google.common.collect.ImmutableList;
import com.zheleznyakov.lingvo.basic.dictionary.Record;
import com.zheleznyakov.lingvo.basic.exercisers.Exercise;

public class MainFormExercise implements Exercise {
    public final String mainForm;
    public final String partOfSpeech;
    public final String description;
    public final String transcription;
    public final ImmutableList<Record.UsageExample> usageExamples;

    MainFormExercise(Record record) {
        mainForm = record.word.getMainForm();
        partOfSpeech = record.word.getPartOfSpeech().brief;
        description = null;
        transcription = record.transcription;
        usageExamples = record.examples;
    }
}
