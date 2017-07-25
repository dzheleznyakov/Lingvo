package com.zheleznyakov.lingvo.language.en;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.language.Language;

public class EnConjunctionTest {
    @Test
    public void createEnConjunction() {
        String mainForm = "and";
        EnConjunction and = new EnConjunction(mainForm);
        String[] expectedForms = {mainForm};

        assertEquals(Language.ENGLISH, and.getLanguage());
        assertEquals(PartOfSpeech.CONJUNCTION, and.getPartOfSpeech());
        assertEquals(mainForm, and.getMainForm());
        assertArrayEquals(expectedForms, and.getForms());
        assertArrayEquals(expectedForms, and.getFormsFull());
    }
}
