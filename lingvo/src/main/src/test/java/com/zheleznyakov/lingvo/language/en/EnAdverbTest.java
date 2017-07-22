package com.zheleznyakov.lingvo.language.en;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.language.Language;

public class EnAdverbTest {
    @Test
    public void testCreateEnAdverb() {
        String mainForm = "well";
        EnAdverb well = new EnAdverb(mainForm);
        String[] expectedforms = {mainForm};

        assertEquals(Language.ENGLISH, well.getLanguage());
        assertEquals(PartOfSpeech.ADVERB, well.getPartOfSpeech());
        assertEquals(mainForm, well.getMainForm());
        assertArrayEquals(expectedforms, well.getForms());
        assertArrayEquals(expectedforms, well.getFormsFull());
    }
}
