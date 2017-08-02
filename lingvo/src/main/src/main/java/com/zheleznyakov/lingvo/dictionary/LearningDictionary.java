package com.zheleznyakov.lingvo.dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.Language;

public class LearningDictionary implements Iterable<Word> {

    private Language language;
    private int numberOfRepetitions;
    private Map<Word, Stats> nonLearnedWordsToStats = new HashMap<>();
    private Map<Word, Stats> learnedWordsToStats = new HashMap<>();

    public LearningDictionary(Language language) {
        this.language = language;
    }

    public void add(Word word, String meaning) {
        if (language != word.getLanguage())
            throw new IllegalArgumentException("Adding " + word.getLanguage() + " word to " + language + " dictionary");
        nonLearnedWordsToStats.put(word, new Stats(meaning));

    }

    public Language getLanguage() {
        return language;
    }

    public int getNumberOfCorrectAnswers(Word word) {
        Stats stats = nonLearnedWordsToStats.get(word) == null
                ? learnedWordsToStats.get(word)
                : nonLearnedWordsToStats.get(word);
        return stats.numberOfCorrectAnswers;
    }

    public String getMeaning(Word word) {
        return nonLearnedWordsToStats.get(word).meaning;
    }

    public boolean isLearned(Word word) {
        return numberOfRepetitions <= getNumberOfCorrectAnswers(word);
    }

    public void setNumberOfRepetitions(int numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }

    public void checkWordMeaning(Word word, String meaning) {
        Stats stats = nonLearnedWordsToStats.get(word);
        if (meaning.equals(stats.meaning))
            processCorrectAnswer(word);
        else
            processIncorrectAnswer(stats);
    }

    private void processCorrectAnswer(Word word) {
        Stats stats = nonLearnedWordsToStats.get(word);
        stats.numberOfCorrectAnswers++;
        stats.lastCheckFailed = false;
        if (isLearned(word))
            markAsLearned(word, stats);
    }

    private void markAsLearned(Word word, Stats stats) {
        learnedWordsToStats.put(word, stats);
        nonLearnedWordsToStats.remove(word);
    }

    private void processIncorrectAnswer(Stats stats) {
        if (stats.lastCheckFailed)
            stats.numberOfCorrectAnswers--;
        else
            stats.lastCheckFailed = true;
    }

    public int size() {
        return nonLearnedWordsToStats.size();
    }

    @NotNull
    @Override
    public Iterator<Word> iterator() {
        Set<Word> words = nonLearnedWordsToStats.keySet();
        words.addAll(learnedWordsToStats.keySet());
        return words.iterator();
    }

    @NotNull
    public Collection<Word> nonLearned() {
        return nonLearnedWordsToStats.keySet();
    }

    public Stream<Word> streamWords() {
        return nonLearnedWordsToStats.keySet().stream();
    }

    private static class Stats {
        private String meaning;
        private int numberOfCorrectAnswers = 0;
        private boolean lastCheckFailed = false;

        private Stats(String meaning) {
            this.meaning = meaning;
        }
    }

}
