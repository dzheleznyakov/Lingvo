package com.zheleznyakov.lingvo.basic.dictionary;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary.RecordAdder;
import com.zheleznyakov.lingvo.basic.persistence.Persistable;
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord;

public class Record {
    @Persistable
    public final GrammaticalWord word;
    @Persistable
    public final String description;
    @Persistable
    public final String transcription;
    @Persistable
    public final ImmutableList<UsageExample> examples;

    Record(RecordAdder builder) {
        word = builder.word;
        description = builder.description;
        transcription = builder.transcription;
        examples = builder.examples;
    }

    public static class UsageExample {
        @Persistable
        public final String example;
        @Persistable
        public final String translation;

        public UsageExample(String example, String translation) {
            this.example = example;
            this.translation = translation;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("example", example)
                    .add("translation", translation)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("word", word)
                .add("description", description)
                .add("transcription", transcription)
                .add("examples", examples)
                .toString();
    }
}
