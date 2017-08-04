package com.zheleznyakov.lingvo.learning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.language.en.EnNoun;
import com.zheleznyakov.lingvo.language.en.EnPronounPattern;
import com.zheleznyakov.lingvo.language.en.EnVerb;
import com.zheleznyakov.lingvo.language.en.EnVerbConjugation;

public class LearningDictionaryTest extends LearningBaseTest {

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
    public void twoInstancesOfTheSameWordMakeTwoEntries() {
        EnNoun word2 = EnNoun.build(word.getMainForm());
        dictionary.add(word2, "different meaning");

        assertEquals(2, dictionary.size());
    }

    @Test
    public void testGetMultiFormWords() {
        EnVerb verb = EnVerb.build("call");
        dictionary.add(verb, "звонить");
        Set<EnNoun> nouns = dictionary.getMultiFormWords(EnNoun.class);
        Set<EnVerb> verbs = dictionary.getMultiFormWords(EnVerb.class);

        assertEquals(1, nouns.size());
        assertTrue(nouns.contains(word));
        assertEquals(1, verbs.size());
        assertTrue(verbs.contains(verb));
    }

    @Test
    public void testCheckForms_PresentSimple() {
        EnVerb verb = EnVerb.build("call");
        dictionary.add(verb, "звонить");
        dictionary.checkWordForms(word, EnVerbConjugation.PRESENT_SIMPLE)
                .add(EnPronounPattern.FIRST_PERSON_SINGLE, "call")
                .submit();
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
