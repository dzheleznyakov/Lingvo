package com.zheleznyakov.lingvo.basic.dictionary;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.zheleznyakov.lingvo.basic.dictionary.Record.UsageExample;
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord;
import com.zheleznyakov.lingvo.basic.words.Language;
import com.zheleznyakov.lingvo.util.Util;

public class LearningDictionary {
    private final Language language;
    private List<Record> records = new LinkedList<>();

    public LearningDictionary(Language language) {
        this.language = language;
    }

    public RecordBuilder record(GrammaticalWord word, String meaning) {
        return new RecordBuilder(word, meaning);
    }

    public List<Record> getRecords() {
        return records;
    }

    class RecordBuilder {
        GrammaticalWord word;
        String meaning;
        String transcription;
        ImmutableList<UsageExample> examples;

        RecordBuilder(GrammaticalWord word, String meaning) {
            this.word = word;
            this.meaning = meaning;
        }

        public RecordBuilder withTranscription(String transcription) {
            this.transcription = transcription;
            return this;
        }

        public RecordBuilder withUsageExamples(Collection<UsageExample> examples) {
            this.examples = ImmutableList.copyOf(examples);
            return this;
        }

        public void add() {
            Util.validateArgument(word.getLanguage() == language,
                    "Illegal language of a word: required [{}], found [{}]", language, word.getLanguage());
            records.add(new Record(this));
        }
    }
}
