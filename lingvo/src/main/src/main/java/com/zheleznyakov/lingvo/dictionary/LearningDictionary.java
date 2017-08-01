package com.zheleznyakov.lingvo.dictionary;

import java.util.HashMap;
import java.util.Map;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.Language;

public class LearningDictionary {

    private Language language;
    private int numberOfRepetitions;
    private Map<Word, Stats> wordsToStats = new HashMap<>();

    public LearningDictionary(Language language) {
        this.language = language;
    }

    public void add(Word word, String meaning) {
        if (language != word.getLanguage())
            throw new IllegalArgumentException("Adding " + word.getLanguage() + " word to " + language + " dictionary");
        wordsToStats.put(word, new Stats(meaning));

    }

    public Language getLanguage() {
        return language;
    }

    public int getNumberOfCorrectAnswers(Word word) {
        return wordsToStats.get(word).numberOfCorrectAnswers;
    }

    public String getMeaning(Word word) {
        return wordsToStats.get(word).meaning;
    }

    public boolean isLearned(Word word) {
        return numberOfRepetitions == wordsToStats.get(word).numberOfCorrectAnswers;
    }

    public void setNumberOfRepetitions(int numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }

    public void checkWordMeaning(Word word, String meaning) {
        Stats stats = wordsToStats.get(word);
        if (meaning.equals(stats.meaning))
            processCorrectAnswer(stats);
        else
            processIncorrectAnswer(stats);
    }

    private void processCorrectAnswer(Stats stats) {
        stats.numberOfCorrectAnswers++;
        stats.lastCheckFailed = false;
    }

    private void processIncorrectAnswer(Stats stats) {
        if (stats.lastCheckFailed)
            stats.numberOfCorrectAnswers--;
        else
            stats.lastCheckFailed = true;
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
