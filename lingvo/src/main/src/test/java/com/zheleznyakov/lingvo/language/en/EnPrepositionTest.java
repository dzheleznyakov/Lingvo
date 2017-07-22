package com.zheleznyakov.lingvo.language.en;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.language.Language;

public class EnPrepositionTest {
    @Test
    public void testCreateEnPreposition() {
        String mainForm = "of";
        EnPreposition of = new EnPreposition(mainForm);
        String[] expectedForms = {mainForm};

        assertEquals(Language.ENGLISH, of.getLanguage());
        assertEquals(PartOfSpeech.PREPOSITION, of.getPartOfSpeech());
        assertEquals(mainForm, of.getMainForm());
        assertArrayEquals(expectedForms, of.getForms());
        assertArrayEquals(expectedForms, of.getFormsFull());
    }
}
