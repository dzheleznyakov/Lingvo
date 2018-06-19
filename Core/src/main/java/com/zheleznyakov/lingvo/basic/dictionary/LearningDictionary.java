package com.zheleznyakov.lingvo.basic.dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.zheleznyakov.lingvo.basic.dictionary.Record.UsageExample;
import com.zheleznyakov.lingvo.basic.persistence.Persistable;
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord;
import com.zheleznyakov.lingvo.basic.words.Language;
import com.zheleznyakov.lingvo.util.Util;

public class LearningDictionary {
    @Persistable
    private final Language language;
    @Persistable
    private final String name;
    @Persistable
    private LearningDictionaryConfig config = LearningDictionaryConfig.getDefault();
    @Persistable
    private final Map<Record, Stats> records = new HashMap<>();

    public LearningDictionary(Language language, String name) {
        this.language = language;
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public String getName() {
        return name;
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

    public ImmutableSet<Record> getNotLearnedRecords() {
        return records.keySet().stream()
                .filter(record -> getLearnCount(record) < config.getMaxLearnCount())
                .collect(ImmutableSet.toImmutableSet());
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
        return records.get(record).learnCount;
    }

    public void registerCorrectAnswer(Record record) {
        Stats stats = records.get(record);
        stats.learnCount++;
        stats.lastAnswerWasIncorrect = false;
    }

    public void registerIncorrectAnswer(Record record) {
        Stats stats = records.get(record);
        if (stats.lastAnswerWasIncorrect && stats.learnCount > 0)
            stats.learnCount--;
        else if (!stats.lastAnswerWasIncorrect  )
            stats.lastAnswerWasIncorrect = true;
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
            records.put(new Record(this), new Stats());
        }
    }

    private static class Stats {
        private int learnCount = 0;
        private boolean lastAnswerWasIncorrect = false;
    }
}
