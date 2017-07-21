package com.zheleznyakov.lingvo.language.en;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;

public class TestEnNoun {

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

    private void assertDeclensions(EnNoun noun, String[] declensions) {
        assertArrayEquals(declensions, noun.getForms());
    }

    private void assertDeclensionsFull(EnNoun form, String[] declensions) {
        assertArrayEquals(declensions, form.getFormsFull());
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

        assertDeclensions(house, new String[]{"house", "houses", "house's", "houses'"});
    }

    @Test
    public void testNounDeclensions_SibilantEnding() {
        EnNoun box = buildRegularNoun("box");
        EnNoun sandwich = buildRegularNoun("sandwich");
        EnNoun parish = buildRegularNoun("parish");
        EnNoun miss = buildRegularNoun("miss");

        assertDeclensions(box, new String[]{"box", "boxes", "box's", "boxes'"});
        assertDeclensions(sandwich, new String[]{"sandwich", "sandwiches", "sandwich's", "sandwiches'"});
        assertDeclensions(parish, new String[]{"parish", "parishes", "parish's", "parishes'"});
        assertDeclensions(miss, new String[]{"miss", "misses", "miss'", "misses'"});
    }

    @Test
    public void testNounDeclensions_YEnding() {
        EnNoun city = buildRegularNoun("city");
        EnNoun boy = buildRegularNoun("boy");

        assertDeclensions(city, new String[]{"city", "cities", "city's", "cities'"});
        assertDeclensions(boy, new String[]{"boy", "boys", "boy's", "boys'"});
    }

    @Test
    public void testNounDeclensions_FEnding() {
        EnNoun thief = buildRegularNoun("thief");
        EnNoun wife = buildRegularNoun("wife");
        EnNoun cliff = buildRegularNoun("cliff");

        assertDeclensions(thief, new String[]{"thief", "thieves", "thief's", "thieves'"});
        assertDeclensions(wife, new String[]{"wife", "wives", "wife's", "wives'"});
        assertDeclensions(cliff, new String[]{"cliff", "cliffs", "cliff's", "cliffs'"});
    }

    @Test
    public void testNounDeclensions_Irregular() {
        EnNoun man = buildIrregularNoun("man", "men");
        EnNoun hero = buildIrregularNoun("hero", "heroes");

        assertFalse(man.isRegular());
        assertFalse(man.isProperNoun());
        assertDeclensions(man, new String[]{"man", "men", "man's", "men's"});
        assertDeclensions(hero, new String[]{"hero", "heroes", "hero's", "heroes'"});
    }

    @Test
    public void testAlternativeForms_NounHasAlternativeForm() {
        EnNoun realization = getNounWithAlternativeForm("realization", "realisation");

        assertDeclensionsFull(realization, new String[]{
                "realization/realisation",
                "realizations/realisations",
                "realization's/realisation's",
                "realizations'/realisations'"});
    }

    @Test
    public void testAlternativeForm_NounDoesNotHaveAlternativeForm() {
        EnNoun house = buildRegularNoun("house");

        assertDeclensionsFull(house, new String[]{"house", "houses", "house's", "houses'"});
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
        assertDeclensions(thomas, new String[]{"Thomas", "Thomas's"});
        assertDeclensionsFull(thomas, new String[]{"Thomas", "Thomas's/Thomas'"});
    }
}
