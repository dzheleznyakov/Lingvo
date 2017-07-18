package com.zheleznyakov.lingvo.language.en;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;

public class TestEnglishVerb {

    @Test
    public void testCreateEnVerb() {
        String verbString = "abc";
        EnVerb verb = EnVerb.builder(verbString)
                .build();

        assertEquals(Language.ENGLISH, verb.getLanguage());
        assertEquals(PartOfSpeech.VERB, verb.getPartOfSpeech());
    }
}
