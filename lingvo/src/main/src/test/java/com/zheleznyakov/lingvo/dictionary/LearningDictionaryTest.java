package com.zheleznyakov.lingvo.dictionary;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.language.en.EnNoun;

public class LearningDictionaryTest {

    private LearningDictionary dictionary;
    private Word word;
    private String meaning;
    private int numberOfRepetitions = 10;

    @Before
    public void setUp() throws Exception {
        dictionary = new LearningDictionary(Language.ENGLISH);
        word = EnNoun.build("word");
        meaning = "meaning";
        dictionary.add(word, meaning);
        dictionary.setNumberOfRepetitions(numberOfRepetitions);
    }

    private void exerciseWordCorrectly(int number) {
        for (int i = 0; i < number; i++)
            dictionary.checkWordMeaning(word, meaning);
    }

    private void exerciseWordIncorrectly(int number) {
        for (int i = 0; i < number; i++)
            dictionary.checkWordMeaning(word, meaning + "a");
    }

    private void assertWordLearningProgress(int correctScore, boolean isLearned) {
        assertEquals(correctScore, dictionary.getNumberOfCorrectAnswers(word));
        assertEquals(isLearned, dictionary.isLearned(word));
    }

    @Test
    public void createLearningDictionaryAndAddWord() {
        assertEquals(Language.ENGLISH, dictionary.getLanguage());
        assertEquals(meaning, dictionary.getMeaning(word));
        assertWordLearningProgress(0, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddingWordInWrongLanguageThrow() {
        Word spanishWord = new SpanishWord();
        dictionary.add(spanishWord, meaning);
    }

    @Test
    public void learnWordToHalf() {
        exerciseWordCorrectly(numberOfRepetitions / 2);
        assertWordLearningProgress(numberOfRepetitions / 2, false);
        assertEquals(numberOfRepetitions / 2, dictionary.getNumberOfCorrectAnswers(word));
    }

    @Test
    public void learnWordCompletely() {
        exerciseWordCorrectly(numberOfRepetitions);
        assertWordLearningProgress(numberOfRepetitions, true);
    }

    @Test
    public void learnWordCompletelyWithOneMinorSetback() {
        exerciseWordCorrectly(1);
        exerciseWordIncorrectly(1);
        exerciseWordCorrectly(numberOfRepetitions - 1);

        assertWordLearningProgress(numberOfRepetitions, true);
    }

    @Test
    public void learnWordCompletelyWithOneMajorSetback() {
        exerciseWordCorrectly(2);
        exerciseWordIncorrectly(2);
        exerciseWordCorrectly(numberOfRepetitions - 2);
        exerciseWordCorrectly(1);

        assertWordLearningProgress(numberOfRepetitions, true);

    }

    @Test
    public void learnWordCompletelyWithOneMajorAndOneMinorSetbacks() {
        exerciseWordCorrectly(2);
        exerciseWordIncorrectly(2);
        exerciseWordCorrectly(numberOfRepetitions - 2);
        exerciseWordIncorrectly(1);
        exerciseWordCorrectly(1);

        assertWordLearningProgress(numberOfRepetitions, true);
    }

    private static class SpanishWord implements Word {
        @Override
        public Language getLanguage() {
            return Language.SPANISH;
        }

        @Override
        public PartOfSpeech getPartOfSpeech() {
            return null;
        }

        @Override
        public String getMainForm() {
            return null;
        }
    }

}
