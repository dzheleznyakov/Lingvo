package com.zheleznyakov.lingvo.language.en;

import static com.zheleznyakov.testutils.ZhAssert.assertWordForms;
import static com.zheleznyakov.testutils.ZhAssert.assertWordFormsFull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.language.Language;

public class EnPrepositionTest {
    @Test
    public void testCreateEnPreposition() {
        String mainForm = "of";
        EnPreposition of = EnPreposition.build(mainForm);
        String[] expectedForms = {mainForm};

        assertEquals(Language.ENGLISH, of.getLanguage());
        assertEquals(PartOfSpeech.PREPOSITION, of.getPartOfSpeech());
        assertEquals(mainForm, of.getMainForm());
        assertWordForms(of, expectedForms);
        assertWordFormsFull(of, expectedForms);
    }

    @Test
    public void testGetTranscription() {
        EnPreposition from = EnPreposition.builder("from")
                .transcription("frɒm")
                .build();

        assertEquals("[frɒm]", from.getTranscription());
    }
}
