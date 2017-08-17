package com.zheleznyakov.lingvo.language.en.word;

import static com.zheleznyakov.lingvo.language.en.word.EnVerb.EnVerbFormName.GERUND;
import static com.zheleznyakov.lingvo.language.en.word.EnVerb.EnVerbFormName.PAST_PARTICIPLE;
import static com.zheleznyakov.lingvo.language.en.word.EnVerb.EnVerbFormName.PAST_PLURAL;
import static com.zheleznyakov.lingvo.language.en.word.EnVerb.EnVerbFormName.PAST_SINGLE;
import static com.zheleznyakov.lingvo.language.en.word.EnVerb.EnVerbFormName.PRESENT_FIRST_SINGULAR;
import static com.zheleznyakov.lingvo.language.en.word.EnVerb.EnVerbFormName.PRESENT_PLURAL;
import static com.zheleznyakov.lingvo.language.en.word.EnVerb.EnVerbFormName.PRESENT_THIRD_SINGULAR;
import static com.zheleznyakov.testutils.ZhAssert.assertWordForms;
import static com.zheleznyakov.testutils.ZhAssert.assertWordFormsFull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.language.en.word.EnVerb.Builder;
import com.zheleznyakov.lingvo.language.en.word.EnVerb.EnVerbFormName;

public class EnVerbTest {

    private EnVerb buildRegularVerb(String mainForm) {
        return EnVerb.build(mainForm);
    }

    private EnVerb buildIrregularVerb(String mainForm, Object... alternativeForms) {
        if (alternativeForms.length % 2 != 0)
            throw new IllegalArgumentException("The length of alternativeForms should be even");
        EnVerb.Builder verbBuilder = EnVerb.builder(mainForm);
        for (int i = 0; i < alternativeForms.length; i += 2)
            appendAlternativeForm(verbBuilder, alternativeForms[i], alternativeForms[i + 1]);
        return verbBuilder.build();
    }

    private void appendAlternativeForm(Builder verbBuilder, Object formName, Object alternativeForm) {
        EnVerbFormName enVerbFormNameCast = (EnVerbFormName) formName;
        String alternativeFormCast = (String) alternativeForm;
        verbBuilder.irregularForm(enVerbFormNameCast, alternativeFormCast);
    }

    private EnVerb buildVerbWithAlternativeForm(String mainForm, String alternativeForm) {
        return EnVerb.builder(mainForm)
                .alternativeForm(alternativeForm);
    }

    @Test@Ignore
    public void testCreateEnVerb() {
        String verbString = "abc";
        EnVerb verb = buildRegularVerb(verbString);

        assertEquals(Language.ENGLISH, verb.getLanguage());
        assertEquals(PartOfSpeech.VERB, verb.getPartOfSpeech());
        assertTrue(verb.isRegular());
        assertEquals(verbString, verb.getMainForm());
    }

    @Test
    public void testGetForms_Regular() {
        EnVerb test = buildRegularVerb("test");

        assertWordForms(test, "test", "tests", "testing", "tested");
    }

    @Test
    public void testGetForms_SibilantEnding() {
        EnVerb miss = buildRegularVerb("miss");
        EnVerb wish = buildRegularVerb("wish");
        EnVerb match = buildRegularVerb("match");
        EnVerb fix = buildRegularVerb("fix");

        assertWordForms(miss, "miss", "misses", "missing", "missed");
        assertWordForms(wish, "wish", "wishes", "wishing", "wished");
        assertWordForms(match, "match", "matches", "matching", "matched");
        assertWordForms(fix, "fix", "fixes", "fixing", "fixed");
    }

    @Test
    public void testGetForms_EEnding() {
        EnVerb move = buildRegularVerb("move");

        assertWordForms(move, "move", "moves", "moving", "moved");
    }

    @Test
    public void testGetForms_YEnding() {
        EnVerb apply = buildRegularVerb("apply");
        EnVerb employ = buildRegularVerb("employ");

        assertWordForms(apply, "apply", "applies", "applying", "applied");
        assertWordForms(employ, "employ", "employs", "employing", "employed");
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
        assertWordForms(vDo, "do", "does", "doing", "did", "done");
        assertWordForms(be, "be", "am", "is", "are", "being", "was", "were", "been");
    }

    @Test
    public void testGetForms_ShortVerb() {
        EnVerb log = buildRegularVerb("log");
        EnVerb refer = buildRegularVerb("refer");

        assertWordForms(log, "log", "logs", "logging", "logged");
        assertWordForms(refer, "refer", "refers", "referring", "referred");
    }

    @Test
    public void testAlternativeForms_VerbHasAlternativeForm() {
        EnVerb realise = buildVerbWithAlternativeForm("realise", "realize");

        assertWordForms(realise, "realise", "realises", "realising", "realised");
        assertWordFormsFull(realise, "realise/realize",
                "realises/realizes", "realising/realizing", "realised/realized");
    }

    @Test
    public void testAlternativeForms_VerbDoesNotHaveAlternativeForm() {
        EnVerb test = buildRegularVerb("test");

        assertWordFormsFull(test, "test", "tests", "testing", "tested");
    }

    @Test
    public void testGetForm() {
        EnVerb put = buildIrregularVerb("put", PAST_SINGLE, "put",
                PAST_PARTICIPLE, "put");

        assertEquals("put", put.getForm(PAST_PARTICIPLE));
        assertEquals("put", put.getForm(PAST_PLURAL));
    }

    @Test
    public void testVerbPhrase_Regular() {
        EnVerb pullOff = EnVerb.builder("pull")
                .withPhrasePart("off")
                .build();

        assertTrue(pullOff.isRegular());
        assertWordForms(pullOff, "pull off", "pulls off", "pulling off", "pulled off");
        assertWordFormsFull(pullOff, "pull off", "pulls off", "pulling off", "pulled off");
    }

    @Test
    public void testVerbPhrase_AlternativeForm() {
        EnVerb realiseOut = EnVerb.builder("realise")
                .withPhrasePart("out")
                .alternativeForm("realize");

        assertWordFormsFull(realiseOut,
                "realise out/realize out",
                "realises out/realizes out", "realising out/realizing out", "realised out/realized out");
    }

    @Test
    public void testIrregularVerb_FormsCanGoInAnyOrder() {
        EnVerb be1 = buildIrregularVerb("be", GERUND, "being",
                PRESENT_FIRST_SINGULAR, "am", PRESENT_THIRD_SINGULAR, "is", PRESENT_PLURAL, "are",
                PAST_SINGLE, "was", PAST_PLURAL, "were", PAST_PARTICIPLE, "been");
        EnVerb be2 = buildIrregularVerb("be", GERUND, "being",
                PAST_PARTICIPLE, "been", PAST_PLURAL, "were", PAST_SINGLE, "was",
                PRESENT_PLURAL, "are", PRESENT_THIRD_SINGULAR, "is", PRESENT_FIRST_SINGULAR, "am");

        assertArrayEquals(be1.getForms(), be2.getForms());
    }

    @Test
    public void testGetTranscription() {
        Builder var = EnVerb.builder("call");
        var.transcription("kɔːl");
        EnVerb call = var.build();

        assertEquals("[kɔːl]", call.getTranscription());
    }
}
