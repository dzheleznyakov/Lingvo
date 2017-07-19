package com.zheleznyakov.lingvo.language.en;

import static com.zheleznyakov.lingvo.language.en.EnVerb.Form.GERUND;
import static com.zheleznyakov.lingvo.language.en.EnVerb.Form.PAST_PLURAL;
import static com.zheleznyakov.lingvo.language.en.EnVerb.Form.PAST_SINGLE;
import static com.zheleznyakov.lingvo.language.en.EnVerb.Form.PAST_PARTICIPLE;
import static com.zheleznyakov.lingvo.language.en.EnVerb.Form.PRESENT_FIRST_SINGULAR;
import static com.zheleznyakov.lingvo.language.en.EnVerb.Form.PRESENT_PLURAL;
import static com.zheleznyakov.lingvo.language.en.EnVerb.Form.PRESENT_THIRD_SINGULAR;
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
                .irregularForm(PRESENT_THIRD_SINGULAR, "does")
                .irregularForm(PAST_SINGLE, "did")
                .irregularForm(PAST_PARTICIPLE, "done")
                .build();
        EnVerb be = EnVerb.builder("be")
                .irregularForm(GERUND, "being")
                .irregularForm(PRESENT_FIRST_SINGULAR, "am")
                .irregularForm(PRESENT_THIRD_SINGULAR, "is")
                .irregularForm(PRESENT_PLURAL, "are")
                .irregularForm(PAST_SINGLE, "was")
                .irregularForm(PAST_PLURAL, "were")
                .irregularForm(PAST_PARTICIPLE, "been")
                .build();

        assertFalse(vDo.isRegular());
        assertVerbForms(vDo, "do", "does", "doing", "did", "done");
        assertVerbForms(be, "be", "am", "is", "are", "being", "was", "were", "been");
    }
}
