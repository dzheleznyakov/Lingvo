package com.zheleznyakov.lingvo.learning;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.language.en.word.EnNoun;

public abstract class LearningBaseTest {
    protected LearningDictionary dictionary;
    protected Word word;
    protected Word secondWord;
    protected Word thirdWord;
    protected String meaning;
    protected int numberOfRepetitions = 10;

    @Before
    public void setUpBasics() {
        dictionary = new LearningDictionary(Language.ENGLISH);
        word = EnNoun.build("word");
        secondWord = EnNoun.build("another");
        thirdWord = EnNoun.build("other");
        meaning = "meaning";
        dictionary.add(word, meaning);
        dictionary.setNumberOfRepetitions(numberOfRepetitions);
    }

    protected void assertWordLearningProgress(int correctScore, boolean isLearned) {
        assertEquals(correctScore, dictionary.getNumberOfCorrectAnswers(word));
        assertEquals(isLearned, dictionary.isLearned(word));
    }
}
