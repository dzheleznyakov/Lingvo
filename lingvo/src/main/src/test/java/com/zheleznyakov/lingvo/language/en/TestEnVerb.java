package com.zheleznyakov.lingvo.language.en;

import static com.zheleznyakov.lingvo.language.en.EnVerb.FormName.GERUND;
import static com.zheleznyakov.lingvo.language.en.EnVerb.FormName.PAST_PARTICIPLE;
import static com.zheleznyakov.lingvo.language.en.EnVerb.FormName.PAST_PLURAL;
import static com.zheleznyakov.lingvo.language.en.EnVerb.FormName.PAST_SINGLE;
import static com.zheleznyakov.lingvo.language.en.EnVerb.FormName.PRESENT_FIRST_SINGULAR;
import static com.zheleznyakov.lingvo.language.en.EnVerb.FormName.PRESENT_PLURAL;
import static com.zheleznyakov.lingvo.language.en.EnVerb.FormName.PRESENT_THIRD_SINGULAR;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.language.en.EnVerb.Builder;

public class TestEnVerb {

    private EnVerb buildRegularVerb(String mainForm) {
        return EnVerb.builder(mainForm)
                .build();
    }

    private EnVerb buildIrregularVerb(String mainForm, Object... alternativeForms) {
        if (alternativeForms.length % 2 != 0)
            throw new IllegalArgumentException("The length of alternativeForms should be even");
        EnVerb.Builder verbBuilder = EnVerb.builder(mainForm);
        for (int i = 0; i < alternativeForms.length; i += 2) {
            appendAlternativeForm(verbBuilder, alternativeForms[i], alternativeForms[i + 1]);
        }
        return verbBuilder.build();
    }

    private void appendAlternativeForm(Builder verbBuilder, Object formName, Object alternativeForm) {
        EnVerb.FormName formNameCast = (EnVerb.FormName) formName;
        String alternativeFormCast = (String) alternativeForm;
        verbBuilder.irregularForm(formNameCast, alternativeFormCast);
    }

    private EnVerb buildVerbWithAlternativeForm(String mainForm, String alternativeForm) {
        return EnVerb.builder(mainForm)
                .alternativeForm(alternativeForm);
    }

    private void assertVerbForms(EnVerb verb, String... expectedForms) {
        assertArrayEquals(expectedForms, verb.getForms());
    }

    private void assertVerbFormsFull(EnVerb verb, String... expectedForms) {
        assertArrayEquals(expectedForms, verb.getFormsFull());
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
        EnVerb vDo = buildIrregularVerb("do",
                PRESENT_THIRD_SINGULAR, "does",
                PAST_SINGLE, "did", PAST_PARTICIPLE, "done");
        EnVerb be = buildIrregularVerb("be",
                GERUND, "being",
                PRESENT_FIRST_SINGULAR, "am", PRESENT_THIRD_SINGULAR, "is", PRESENT_PLURAL, "are",
                PAST_SINGLE, "was", PAST_PLURAL, "were", PAST_PARTICIPLE, "been");

        assertFalse(vDo.isRegular());
        assertVerbForms(vDo, "do", "does", "doing", "did", "done");
        assertVerbForms(be, "be", "am", "is", "are", "being", "was", "were", "been");
    }

    @Test
    public void testAlternativeForms_VerbHasAlternativeForm() {
        EnVerb realise = buildVerbWithAlternativeForm("realise", "realize");

        assertVerbForms(realise, "realise", "realises", "realising", "realised");
        assertVerbFormsFull(realise, "realise/realize",
                "realises/realizes", "realising/realizing", "realised/realized");
    }

    @Test
    public void testAlternativeForms_VerbDoesNotHaveAlternativeForm() {
        EnVerb test = buildRegularVerb("test");

        assertVerbFormsFull(test, "test", "tests", "testing", "tested");
    }
}
