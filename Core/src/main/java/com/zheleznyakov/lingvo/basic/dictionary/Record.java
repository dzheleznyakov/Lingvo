package com.zheleznyakov.lingvo.basic.dictionary;

import com.google.common.collect.ImmutableList;
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary.RecordAdder;
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord;

public class Record {
    public final GrammaticalWord word;
    public final String description;
    public final String transcription;
    public final ImmutableList<UsageExample> examples;

    Record(RecordAdder builder) {
        word = builder.word;
        description = builder.description;
        transcription = builder.transcription;
        examples = builder.examples;
    }

    public static class UsageExample {
        public final String example;
        public final String translation;

        public UsageExample(String example, String translation) {
            this.example = example;
            this.translation = translation;
        }
    }


}
