package com.zheleznyakov.lingvo.learning;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.basic.ChangeFormPattern;
import com.zheleznyakov.lingvo.basic.MultiFormWord;
import com.zheleznyakov.lingvo.basic.PronounPattern;
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

    public void registerCorrectAnswer(Word word) {
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

    public void registerIncorrectAnswer(Word word) {
        Stats stats = nonLearnedWordsToStats.get(word);
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
        Set<Word> words = getWords();
        return words.iterator();
    }

    @NotNull
    private Set<Word> getWords() {
        Set<Word> words = new HashSet<>(nonLearnedWordsToStats.keySet());
        words.addAll(learnedWordsToStats.keySet());
        return words;
    }

    @NotNull
    public Collection<Word> nonLearned() {
        return nonLearnedWordsToStats.keySet();
    }

    @NotNull
    public Stream<Word> streamWords() {
        return getWords().stream();
    }

    @NotNull
    public Stream<Word> streamNonLearnedWords() {
        return nonLearnedWordsToStats.keySet().stream();
    }

    public <T extends MultiFormWord> Set<T> getMultiFormWords(Class<T> wordClass) {
        return this.streamWords()
                .filter(wordClass::isInstance)
                .map(wordClass::cast)
                .collect(Collectors.toSet());
    }

    public FormCard checkWordForms(Word word, ChangeFormPattern pattern) {
        return new FormCard(word, pattern);
    }

    private static class Stats {
        private String meaning;
        private int numberOfCorrectAnswers = 0;
        private boolean lastCheckFailed = false;

        private Stats(String meaning) {
            this.meaning = meaning;
        }
    }

    public class FormCard {
        private final Word word;
        private final ChangeFormPattern pattern;
        private final Map<PronounPattern, String> answers = new HashMap<>();

        public FormCard(Word word, ChangeFormPattern pattern) {
            this.word = word;
            this.pattern = pattern;
        }

        public FormCard add(PronounPattern formName, String form) {
            answers.put(formName, form);
            return this;
        }

        public void submit() {

        }
    }


}
