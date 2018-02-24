package com.zheleznyakov.lingvo.basic.dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.zheleznyakov.lingvo.basic.dictionary.Record.UsageExample;
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord;
import com.zheleznyakov.lingvo.basic.words.Language;
import com.zheleznyakov.lingvo.util.Util;

public class LearningDictionary {
    private final Language language;
    private Map<Record, Integer> records = new HashMap<>();
    private LearningDictionaryConfig config = LearningDictionaryConfig.getDefault();

    public LearningDictionary(Language language) {
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }

    public LearningDictionaryConfig getConfig() {
        return config;
    }

    public RecordAdder record(GrammaticalWord word, String description) {
        return new RecordAdder(word, description);
    }

    public void remove(Record record) {
        records.remove(record);
    }

    public ImmutableSet<Record> getRecords() {
        return ImmutableSet.copyOf(records.keySet());
    }

    public void updateDescription(Record record, String description) {
        records.remove(record);
        record(record.word, description)
                .withTranscription(record.transcription)
                .withUsageExamples(record.examples)
                .add();
    }

    public void updateTranscription(Record record, String transcription) {
        records.remove(record);
        record(record.word, record.description)
                .withTranscription(transcription)
                .withUsageExamples(record.examples)
                .add();
    }

    public void addExample(Record record, UsageExample example) {
        records.remove(record);
        record(record.word, record.description)
                .withTranscription(record.transcription)
                .withUsageExamples(record.examples)
                .withUsageExample(example)
                .add();
    }

    public void removeExample(Record record, UsageExample example) {
        records.remove(record);
        ImmutableList<UsageExample> updatedExamples = record.examples.stream()
                .filter(ex -> !ex.equals(example))
                .collect(ImmutableList.toImmutableList());
        record(record.word, record.description)
                .withTranscription(record.transcription)
                .withUsageExamples(updatedExamples)
                .add();
    }

    public void updateExample(Record record, UsageExample oldExample, UsageExample newExample) {
        records.remove(record);
        ImmutableList<UsageExample> updatedExamples = record.examples.stream()
                .map(ex -> ex.equals(oldExample) ? newExample : ex)
                .collect(ImmutableList.toImmutableList());
        record(record.word, record.description)
                .withTranscription(record.transcription)
                .withUsageExamples(updatedExamples)
                .add();
    }

    public int getLearnCount(Record record) {
        return records.get(record);
    }

    public void increaseLearnCount(Record record) {
        records.compute(record, (rec, count) -> ++count);
    }

    public class RecordAdder {
        GrammaticalWord word;
        String description;
        String transcription;
        ImmutableList<UsageExample> examples;
        private ImmutableList.Builder<UsageExample> exampleListBuilder = ImmutableList.builder();

        RecordAdder(GrammaticalWord word, String description) {
            this.word = word;
            this.description = description;
        }

        public RecordAdder withTranscription(String transcription) {
            this.transcription = transcription;
            return this;
        }

        public RecordAdder withUsageExample(UsageExample example) {
            exampleListBuilder.add(example);
            return this;
        }

        public RecordAdder withUsageExamples(Collection<UsageExample> examples) {
            exampleListBuilder.addAll(examples);
            return this;
        }

        public void add() {
            Util.validateArgument(word.getLanguage() == language,
                    "Illegal language of a word: required [{}], found [{}]", language, word.getLanguage());
            examples = exampleListBuilder.build();
            records.put(new Record(this), 0);
        }
    }
}
