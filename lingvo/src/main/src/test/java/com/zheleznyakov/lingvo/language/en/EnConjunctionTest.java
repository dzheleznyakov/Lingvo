package com.zheleznyakov.lingvo.language.en;

import static com.zheleznyakov.testutils.ZhAssert.assertWordForms;
import static com.zheleznyakov.testutils.ZhAssert.assertWordFormsFull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.language.Language;

public class EnConjunctionTest {
    @Test
    public void createEnConjunction() {
        String mainForm = "and";
        EnConjunction and = EnConjunction.build(mainForm);
        String[] expectedForms = {mainForm};

        assertEquals(Language.ENGLISH, and.getLanguage());
        assertEquals(PartOfSpeech.CONJUNCTION, and.getPartOfSpeech());
        assertEquals(mainForm, and.getMainForm());
        assertWordForms(and, expectedForms);
        assertWordFormsFull(and, expectedForms);
    }

    @Test
    public void testGetTranscription() {
        EnConjunction or = EnConjunction.builder("or")
                .transcription("ɔː")
                .build();

        assertEquals("[ɔː]", or.getTranscription());
    }
}
