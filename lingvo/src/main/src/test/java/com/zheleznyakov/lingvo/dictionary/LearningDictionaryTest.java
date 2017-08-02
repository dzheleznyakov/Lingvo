package com.zheleznyakov.lingvo.dictionary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.language.en.EnNoun;
import com.zheleznyakov.lingvo.language.en.EnVerb;
import com.zheleznyakov.testutils.ZhSets;

public class LearningDictionaryTest {

    private LearningDictionary dictionary;
    private Word word;
    private Word secondWord;
    private Word thirdWord;
    private String meaning;
    private int numberOfRepetitions = 10;

    @Before
    public void setUp() throws Exception {
        dictionary = new LearningDictionary(Language.ENGLISH);
        word = EnNoun.build("word");
        secondWord = EnNoun.build("another");
        thirdWord = EnNoun.build("other");
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

    private Set<Word> addAdditionalWordsToDictionaryAndCollectAllWordsToSet() throws IllegalAccessException, InstantiationException {
        dictionary.add(secondWord, "second meaning");
        dictionary.add(thirdWord, "third meaning");
        return ZhSets.newSet(HashSet.class, word, secondWord, thirdWord);
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

    @Test
    public void sameWordTakeOneRecordInDictionary() {
        Word word1 = EnNoun.build(word.getMainForm());
        dictionary.add(word1, meaning);

        assertEquals(1, dictionary.size());
    }


    @Test
    public void differentWordsWithTheSameMainFormTakeTwoRecords() {
        Word word1 = EnVerb.build(word.getMainForm());
        dictionary.add(word1, meaning);

        assertEquals(2, dictionary.size());
    }

    @Test
    public void testDictionaryIterator() throws InstantiationException, IllegalAccessException {
        Set<Word> words = addAdditionalWordsToDictionaryAndCollectAllWordsToSet();
        for (Word w : dictionary)
            words.remove(w);

        assertTrue(words.isEmpty());
    }

    @Test
    public void testDictionaryStreams() throws InstantiationException, IllegalAccessException {
        Set<Word> words = addAdditionalWordsToDictionaryAndCollectAllWordsToSet();
        dictionary.streamWords()
                .forEach(words::remove);

        assertTrue(words.isEmpty());
    }

    @Test
    public void testDictionaryNonLearnedIterator() throws InstantiationException, IllegalAccessException {
        Set<Word> words = addAdditionalWordsToDictionaryAndCollectAllWordsToSet();
        exerciseWordCorrectly(numberOfRepetitions);
        for (Word w : dictionary.nonLearned())
            words.remove(w);

        assertTrue(dictionary.isLearned(word));
        assertEquals(ImmutableSet.of(word), words);
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
