package com.zheleznyakov.lingvo.learning;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.zheleznyakov.lingvo.basic.Language;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.dictionary.Dictionary;

public class LearningDictionary extends Dictionary {

    private int maxLearningCount = 30;
    private Map<Word, Statistics> wordsToStatistics = new HashMap<>();

    public LearningDictionary(Language language) {
        super(language);
    }

    @Override
    public void add(Word word, String meaning) {
        super.add(word, meaning);
        wordsToStatistics.put(word, new Statistics());
    }

    public int getCount(Word word) {
        return wordsToStatistics.get(word).getCount();
    }

    public void setMaxLearningCount(int maxLearningCount) {
        this.maxLearningCount = maxLearningCount;
    }

    public void registerAttempt(Word word, boolean successful) {
        Statistics statistics = wordsToStatistics.get(word);
        if (successful)
            statistics.registerSuccessfulAttempt();
        else
            statistics.registerUnsuccessfulAttempt();
    }

    public boolean isLearned(Word word) {
        return wordsToStatistics.get(word).getCount() == maxLearningCount;
    }

    public static class Statistics implements Serializable {
        private int count = 0;
        boolean previousAnswerWasCorrect = true;

        public int getCount() {
            return count;
        }

        private void registerSuccessfulAttempt() {
            count++;
        }

        private void registerUnsuccessfulAttempt() {
            if (previousAnswerWasCorrect)
                previousAnswerWasCorrect = false;
            else
                count = Math.max(0, --count);
        }
    }

}
