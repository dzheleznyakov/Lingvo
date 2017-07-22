package com.zheleznyakov.lingvo.language.en;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.language.Language;

public class EnAdjectiveTest {

    @Test
    public void testEnAdjective() {
        String adjString = "abc";
        EnAdjective adj = new EnAdjective(adjString);
        String[] expectedForms = {adjString};

        assertEquals(Language.ENGLISH, adj.getLanguage());
        assertEquals(PartOfSpeech.ADJECTIVE, adj.getPartOfSpeech());
        assertEquals(adjString, adj.getMainForm());
        assertArrayEquals(expectedForms, adj.getForms());
        assertArrayEquals(expectedForms, adj.getFormsFull() );
    }
}
