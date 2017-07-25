package com.zheleznyakov.lingvo.language.en;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.language.en.EnNoun.EnNounFormName;
import com.zheleznyakov.lingvo.language.en.EnProperNoun.EnProperNounFormName;
import com.zheleznyakov.lingvo.language.en.EnVerb.EnVerbFormName;

public class EnNounTest {

    private EnNoun buildRegularNoun(String noun) {
        return EnNoun.builder(noun).build();
    }

    private EnNoun buildIrregularNoun(String mainForm, String pluralForm) {
        return EnNoun.builder(mainForm)
                .irregularPlural(pluralForm);
    }

    private EnNoun getNounWithAlternativeForm(String mainForm, String alternativeForm) {
        return EnNoun.builder(mainForm)
                .alternativeForm(alternativeForm);
    }

    private EnNoun buildProperNoun(String mainForm) {
        return EnNoun.builder(mainForm)
                .properNoun();
    }

    private void assertForms(EnNoun noun, String... forms) {
        assertArrayEquals(forms, noun.getForms());
    }

    private void assertFormsFull(EnNoun form, String... forms) {
        assertArrayEquals(forms, form.getFormsFull());
    }

    @Test
    public void testCreateEnNoun() {
        String nounString = "abc";
        EnNoun noun = buildRegularNoun(nounString);

        assertEquals(Language.ENGLISH, noun.getLanguage());
        assertEquals(PartOfSpeech.NOUN, noun.getPartOfSpeech());
        assertTrue(noun.isRegular());
        assertFalse(noun.isProperNoun());
        assertEquals(nounString, noun.getMainForm());
    }

    @Test
    public void testNounForms_StandardEnding() {
        EnNoun house = buildRegularNoun("house");

        assertForms(house, "house", "houses", "house's", "houses'");
    }

    @Test
    public void testNounForms_SibilantEnding() {
        EnNoun box = buildRegularNoun("box");
        EnNoun sandwich = buildRegularNoun("sandwich");
        EnNoun parish = buildRegularNoun("parish");
        EnNoun miss = buildRegularNoun("miss");

        assertForms(box, "box", "boxes", "box's", "boxes'");
        assertForms(sandwich, "sandwich", "sandwiches", "sandwich's", "sandwiches'");
        assertForms(parish, "parish", "parishes", "parish's", "parishes'");
        assertForms(miss, "miss", "misses", "miss'", "misses'");
    }

    @Test
    public void testNounForms_YEnding() {
        EnNoun city = buildRegularNoun("city");
        EnNoun boy = buildRegularNoun("boy");

        assertForms(city, "city", "cities", "city's", "cities'");
        assertForms(boy, "boy", "boys", "boy's", "boys'");
    }

    @Test
    public void testNounForms_FEnding() {
        EnNoun thief = buildRegularNoun("thief");
        EnNoun wife = buildRegularNoun("wife");
        EnNoun cliff = buildRegularNoun("cliff");

        assertForms(thief, "thief", "thieves", "thief's", "thieves'");
        assertForms(wife, "wife", "wives", "wife's", "wives'");
        assertForms(cliff, "cliff", "cliffs", "cliff's", "cliffs'");
    }

    @Test
    public void testNounForms_Irregular() {
        EnNoun man = buildIrregularNoun("man", "men");
        EnNoun hero = buildIrregularNoun("hero", "heroes");

        assertFalse(man.isRegular());
        assertFalse(man.isProperNoun());
        assertForms(man, "man", "men", "man's", "men's");
        assertForms(hero, "hero", "heroes", "hero's", "heroes'");
    }

    @Test
    public void testAlternativeForms_NounHasAlternativeForm() {
        EnNoun realization = getNounWithAlternativeForm("realization", "realisation");

        assertFormsFull(realization,
                "realization/realisation",
                "realizations/realisations",
                "realization's/realisation's",
                "realizations'/realisations'");
    }

    @Test
    public void testAlternativeForm_NounDoesNotHaveAlternativeForm() {
        EnNoun house = buildRegularNoun("house");

        assertFormsFull(house, "house", "houses", "house's", "houses'");
    }

    @Test
    public void testProperNoun() {
        EnNoun john = buildProperNoun("John");
        EnNoun thomas = buildProperNoun("Thomas");

        String[] johnDeclensions = {"John", "John's"};

        assertTrue(john.isProperNoun());
        assertTrue(john.isRegular());
        assertForms(john, johnDeclensions);
        assertFormsFull(john, johnDeclensions);
        assertForms(thomas, "Thomas", "Thomas's");
        assertFormsFull(thomas, "Thomas", "Thomas's/Thomas'");
    }

    @Test
    public void testGetForm_NonProperNoun() {
        EnNoun man = buildIrregularNoun("man", "men");

        assertEquals("man", man.getForm(EnNounFormName.NOMINATIVE_SINGLE));
        assertEquals("men", man.getForm(EnNounFormName.NOMINATIVE_PLURAL));
        assertEquals("man's", man.getForm(EnNounFormName.POSSESSIVE_SINGLE));
        assertEquals("men's", man.getForm(EnNounFormName.POSSESSIVE_PLURAL));
    }

    @Test
    public void testGetForm_ProperNoun() {
        EnNoun john = buildProperNoun("John");

        assertEquals("John", john.getForm(EnProperNounFormName.NOMINATIVE_SINGLE));
        assertEquals("John's", john.getForm(EnProperNounFormName.POSSESSIVE_SINGLE));
    }

    @Test
    public void testGetForm_UnknownForm() {
        EnNoun noun = buildRegularNoun("abc");

        assertEquals(noun.getMainForm(), noun.getForm(EnVerbFormName.MAIN_FORM));
    }
}
