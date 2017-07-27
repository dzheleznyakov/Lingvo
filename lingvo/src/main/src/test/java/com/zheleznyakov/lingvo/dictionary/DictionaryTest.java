package com.zheleznyakov.lingvo.dictionary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.language.en.EnNoun;

public class DictionaryTest {

    private Dictionary dictionary;

    @Before
    public void setUp() throws Exception {
        dictionary = new Dictionary(Language.ENGLISH);
    }

    @Test
    public void testLanguageOfDictionary() {
        assertEquals(dictionary.getLanguage(), Language.ENGLISH);
    }

    @Test
    public void testEmptyDictionary() {
        assertTrue(dictionary.isEmpty());
    }

    @Test
    public void testAddingToAndRemovingFromDictionary() {
        Word word = EnNoun.build("abc");

        assertTrue(dictionary.add(word));
        assertFalse(dictionary.isEmpty());
        assertEquals(1, dictionary.size());
        assertTrue(dictionary.contains(word));
        assertFalse(dictionary.contains(EnNoun.build("def")));

        assertTrue(dictionary.remove(word));
        assertTrue(dictionary.isEmpty());
        assertFalse(dictionary.contains(word));
        assertFalse(dictionary.contains(EnNoun.build("def")));
    }
}
