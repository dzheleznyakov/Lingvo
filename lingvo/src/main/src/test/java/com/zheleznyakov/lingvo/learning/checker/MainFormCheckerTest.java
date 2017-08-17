package com.zheleznyakov.lingvo.learning.checker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.en.word.EnNoun;
import com.zheleznyakov.lingvo.learning.LearningBaseTest;
import com.zheleznyakov.testutils.ZhSets;

public class MainFormCheckerTest extends LearningBaseTest {

    private MainFormChecker mainFormChecker;

    @Before
    public void setUp() {
        mainFormChecker = new MainFormChecker(dictionary);
    }

    private void exerciseWordCorrectly(int number) {
        for (int i = 0; i < number; i++)
            assertTrue(mainFormChecker.exercise(word, meaning));
    }

    private void exerciseWordIncorrectly(int number) {
        for (int i = 0; i < number; i++)
            assertFalse(mainFormChecker.exercise(word, meaning + "a"));
    }

    private Set<Word> addAdditionalWordsToDictionaryAndCollectAllWordsToSet() throws IllegalAccessException, InstantiationException {
        dictionary.add(secondWord, "second meaning");
        dictionary.add(thirdWord, "third meaning");
        return ZhSets.newSet(HashSet.class, word, secondWord, thirdWord);
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
        exerciseWordCorrectly(2);
        exerciseWordIncorrectly(1);
        exerciseWordCorrectly(numberOfRepetitions - 2);

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
    public void testDictionaryIterator() throws InstantiationException, IllegalAccessException {
        Set<Word> words = addAdditionalWordsToDictionaryAndCollectAllWordsToSet();
        exerciseWordCorrectly(numberOfRepetitions);
        for (Word w : dictionary)
            words.remove(w);

        assertTrue(words.isEmpty());
    }

    @Test
    public void testDictionaryStream() throws InstantiationException, IllegalAccessException {
        Set<Word> words = addAdditionalWordsToDictionaryAndCollectAllWordsToSet();
        exerciseWordCorrectly(numberOfRepetitions);
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

    @Test
    public void testDictionaryNonLearnedStream() throws InstantiationException, IllegalAccessException {
        Set<Word> words = addAdditionalWordsToDictionaryAndCollectAllWordsToSet();
        exerciseWordCorrectly(numberOfRepetitions);
        dictionary.streamNonLearnedWords()
                .forEach(words::remove);

        assertTrue(dictionary.isLearned(word));
        assertEquals(ImmutableSet.of(word), words);
    }

    @Ignore
    @Test
    public void testPartialAnswer() {
        Word house = EnNoun.build("house");
        dictionary.add(house, "дом, здание");
        mainFormChecker.exercisePartialAnswer(house, "дом");

        assertEquals(1, dictionary.getNumberOfCorrectAnswers(house));
    }

// test partial answer when learning main form
}
