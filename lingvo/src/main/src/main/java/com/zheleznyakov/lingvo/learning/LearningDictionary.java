package com.zheleznyakov.lingvo.learning;

import java.util.HashMap;
import java.util.Map;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.dictionary.Dictionary;
import com.zheleznyakov.lingvo.language.Language;

public class LearningDictionary extends Dictionary {

    private Map<Word, Statistics> wordsToStatistics = new HashMap<>();

    public LearningDictionary(Language language) {
        super(language);
    }

    @Override
    public void add(Word word, String meaning) {
        super.add(word, meaning);
        wordsToStatistics.put(word, new Statistics());
    }

    public Statistics getStatistics(Word word) {
        return wordsToStatistics.get(word);
    }

//    public void setMaxLearningCount(int maxLearningCount) {
//    }

    public void exercise(Word word, MeaningCategory category, String meaning) {
        Statistics statistics = wordsToStatistics.get(word);
        String correctMeaning = getMeaning(word);
        if (correctMeaning.equals(meaning))
            statistics.registerCorrectAnswer(category);
        else
            statistics.registerIncorrectAnswer(category);
    }

    public static class Statistics {
        private Map<WordCategory, Integer> countInCategory = new HashMap<>();
        private Map<WordCategory, Boolean> previousCorrectAnswerInCategory = new HashMap<>();

        public int getCount(WordCategory category) {
            return countInCategory.computeIfAbsent(category, c -> 0);
        }

        private void registerCorrectAnswer(WordCategory category) {
            int count = getCount(category);
            countInCategory.put(category, ++count);
            previousCorrectAnswerInCategory.put(category, true);
        }

        private void registerIncorrectAnswer(WordCategory category) {
            int count = getCount(category);
            if (previousCorrectAnswerInCategory.get(category))
                previousCorrectAnswerInCategory.put(category, false);
            else
                countInCategory.put(category, --count);
        }
    }

}
