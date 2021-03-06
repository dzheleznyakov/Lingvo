package com.zheleznyakov.lingvo.basic.exercisers.mainform;

import com.google.common.collect.ImmutableList;
import com.zheleznyakov.lingvo.basic.dictionary.Record;
import com.zheleznyakov.lingvo.basic.exercisers.Exercise;

public class MainFormExercise implements Exercise {
    public final String mainForm;
    public final String partOfSpeech;
    public final String description;
    public final String transcription;
    public final ImmutableList<String> usageExamples;

    MainFormExercise(Record record, boolean isInForwardMode) {
        mainForm = isInForwardMode ? record.word.getMainForm() : null;
        partOfSpeech = isInForwardMode ? record.word.getPartOfSpeech().brief : null;
        description = isInForwardMode ? null : record.description;
        transcription = isInForwardMode ? record.transcription : null;
        usageExamples = record.examples.stream()
                .map(ue -> isInForwardMode ? ue.example : ue.translation)
                .collect(ImmutableList.toImmutableList());
    }
}
