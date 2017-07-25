package com.zheleznyakov.lingvo.language.en;

import static com.zheleznyakov.testutils.ZhAssert.assertWordForms;
import static com.zheleznyakov.testutils.ZhAssert.assertWordFormsFull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.language.Language;

public class EnAdverbTest {
    @Test
    public void testCreateEnAdverb() {
        String mainForm = "well";
        EnAdverb well = EnAdverb.build(mainForm);
        String[] expectedForms = {mainForm};

        assertEquals(Language.ENGLISH, well.getLanguage());
        assertEquals(PartOfSpeech.ADVERB, well.getPartOfSpeech());
        assertEquals(mainForm, well.getMainForm());
        assertWordForms(well, expectedForms);
        assertWordFormsFull(well, expectedForms);
    }
}
