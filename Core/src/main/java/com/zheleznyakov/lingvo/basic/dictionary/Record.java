package com.zheleznyakov.lingvo.basic.dictionary;

import com.google.common.collect.ImmutableList;
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary.RecordBuilder;
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord;

public class Record {
    public final GrammaticalWord word;
    public final String meaning;
    public final String transcription;
    public final ImmutableList<UsageExample> examples;

    Record(RecordBuilder builder) {
        word = builder.word;
        meaning = builder.meaning;
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
