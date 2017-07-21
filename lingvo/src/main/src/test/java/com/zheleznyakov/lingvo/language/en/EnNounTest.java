package com.zheleznyakov.lingvo.language.en;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.basic.PartOfSpeech;

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

    private void assertDeclensions(EnNoun noun, String... forms) {
        assertArrayEquals(forms, noun.getForms());
    }

    private void assertDeclensionsFull(EnNoun form, String... forms) {
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
    public void testNounDeclensions_StandardEnding() {
        EnNoun house = buildRegularNoun("house");

        assertDeclensions(house, "house", "houses", "house's", "houses'");
    }

    @Test
    public void testNounDeclensions_SibilantEnding() {
        EnNoun box = buildRegularNoun("box");
        EnNoun sandwich = buildRegularNoun("sandwich");
        EnNoun parish = buildRegularNoun("parish");
        EnNoun miss = buildRegularNoun("miss");

        assertDeclensions(box, "box", "boxes", "box's", "boxes'");
        assertDeclensions(sandwich, "sandwich", "sandwiches", "sandwich's", "sandwiches'");
        assertDeclensions(parish, "parish", "parishes", "parish's", "parishes'");
        assertDeclensions(miss, "miss", "misses", "miss'", "misses'");
    }

    @Test
    public void testNounDeclensions_YEnding() {
        EnNoun city = buildRegularNoun("city");
        EnNoun boy = buildRegularNoun("boy");

        assertDeclensions(city, "city", "cities", "city's", "cities'");
        assertDeclensions(boy, "boy", "boys", "boy's", "boys'");
    }

    @Test
    public void testNounDeclensions_FEnding() {
        EnNoun thief = buildRegularNoun("thief");
        EnNoun wife = buildRegularNoun("wife");
        EnNoun cliff = buildRegularNoun("cliff");

        assertDeclensions(thief, "thief", "thieves", "thief's", "thieves'");
        assertDeclensions(wife, "wife", "wives", "wife's", "wives'");
        assertDeclensions(cliff, "cliff", "cliffs", "cliff's", "cliffs'");
    }

    @Test
    public void testNounDeclensions_Irregular() {
        EnNoun man = buildIrregularNoun("man", "men");
        EnNoun hero = buildIrregularNoun("hero", "heroes");

        assertFalse(man.isRegular());
        assertFalse(man.isProperNoun());
        assertDeclensions(man, "man", "men", "man's", "men's");
        assertDeclensions(hero, "hero", "heroes", "hero's", "heroes'");
    }

    @Test
    public void testAlternativeForms_NounHasAlternativeForm() {
        EnNoun realization = getNounWithAlternativeForm("realization", "realisation");

        assertDeclensionsFull(realization,
                "realization/realisation",
                "realizations/realisations",
                "realization's/realisation's",
                "realizations'/realisations'");
    }

    @Test
    public void testAlternativeForm_NounDoesNotHaveAlternativeForm() {
        EnNoun house = buildRegularNoun("house");

        assertDeclensionsFull(house, "house", "houses", "house's", "houses'");
    }

    @Test
    public void testProperNoun() {
        EnNoun john = buildProperNoun("John");
        EnNoun thomas = buildProperNoun("Thomas");

        String[] johnDeclensions = {"John", "John's"};

        assertTrue(john.isProperNoun());
        assertTrue(john.isRegular());
        assertDeclensions(john, johnDeclensions);
        assertDeclensionsFull(john, johnDeclensions);
        assertDeclensions(thomas, "Thomas", "Thomas's");
        assertDeclensionsFull(thomas, "Thomas", "Thomas's/Thomas'");
    }
}
