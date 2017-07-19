package com.zheleznyakov.lingvo.language.en;

import static com.zheleznyakov.lingvo.language.en.EnVerb.Form.PAST;
import static com.zheleznyakov.lingvo.language.en.EnVerb.Form.PAST_PARTICIPLE;
import static com.zheleznyakov.lingvo.language.en.EnVerb.Form.PRESENT_SECOND_SINGULAR;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;

public class TestEnVerb {

    private EnVerb buildRegularVerb(String verb) {
        return EnVerb.builder(verb)
                .build();
    }

    private void assertVerbForms(EnVerb verb, String... expectedForms) {
        assertArrayEquals(expectedForms, verb.getForms());
    }

    @Test
    public void testCreateEnVerb() {
        String verbString = "abc";
        EnVerb verb = buildRegularVerb(verbString);

        assertEquals(Language.ENGLISH, verb.getLanguage());
        assertEquals(PartOfSpeech.VERB, verb.getPartOfSpeech());
        assertEquals(verbString, verb.getMainForm());
    }

    @Test
    public void testGetForms_Regular() {
        EnVerb test = buildRegularVerb("test");

        assertTrue(test.isRegular());
        assertVerbForms(test, "test", "tests", "testing", "tested");
    }

    @Test
    public void testGetForms_SibilantEnding() {
        EnVerb miss = buildRegularVerb("miss");
        EnVerb wish = buildRegularVerb("wish");
        EnVerb match = buildRegularVerb("match");
        EnVerb fix = buildRegularVerb("fix");

        assertVerbForms(miss, "miss", "misses", "missing", "missed");
        assertVerbForms(wish, "wish", "wishes", "wishing", "wished");
        assertVerbForms(match, "match", "matches", "matching", "matched");
        assertVerbForms(fix, "fix", "fixes", "fixing", "fixed");
    }

    @Test
    public void testGetForms_EEnding() {
        EnVerb move = buildRegularVerb("move");

        assertVerbForms(move, "move", "moves", "moving", "moved");
    }

    @Test
    public void testGetForms_YEnding() {
        EnVerb apply = buildRegularVerb("apply");
        EnVerb employ = buildRegularVerb("employ");

        assertVerbForms(apply, "apply", "applies", "applying", "applied");
        assertVerbForms(employ, "employ", "employs", "employing", "employed");
    }

    @Test
    public void testGetForms_Irregular() {
        EnVerb vDo = EnVerb.builder("do")
                .irregularForm(PRESENT_SECOND_SINGULAR, "does")
                .irregularForm(PAST, "did")
                .irregularForm(PAST_PARTICIPLE, "done")
                .build();

        assertFalse(vDo.isRegular());
//        assertVerbForms(vDo, "do", "does", "doing", "did", "done");
    }
}
