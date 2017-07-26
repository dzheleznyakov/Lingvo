package com.zheleznyakov.lingvo.language.en;

import static com.zheleznyakov.testutils.ZhAssert.assertWordForms;
import static com.zheleznyakov.testutils.ZhAssert.assertWordFormsFull;
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

    @Test
    public void testCreateEnNoun() {
        String nounString = "abc";
        EnNoun noun = EnNoun.build(nounString);

        assertEquals(Language.ENGLISH, noun.getLanguage());
        assertEquals(PartOfSpeech.NOUN, noun.getPartOfSpeech());
        assertTrue(noun.isRegular());
        assertFalse(noun.isProperNoun());
        assertEquals(nounString, noun.getMainForm());
    }

    @Test
    public void testNounForms_StandardEnding() {
        EnNoun house = EnNoun.build("house");

        assertWordForms(house, "house", "houses", "house's", "houses'");
    }

    @Test
    public void testNounForms_SibilantEnding() {
        EnNoun box = EnNoun.build("box");
        EnNoun sandwich = EnNoun.build("sandwich");
        EnNoun parish = EnNoun.build("parish");
        EnNoun miss = EnNoun.build("miss");

        assertWordForms(box, "box", "boxes", "box's", "boxes'");
        assertWordForms(sandwich, "sandwich", "sandwiches", "sandwich's", "sandwiches'");
        assertWordForms(parish, "parish", "parishes", "parish's", "parishes'");
        assertWordForms(miss, "miss", "misses", "miss'", "misses'");
    }

    @Test
    public void testNounForms_YEnding() {
        EnNoun city = EnNoun.build("city");
        EnNoun boy = EnNoun.build("boy");

        assertWordForms(city, "city", "cities", "city's", "cities'");
        assertWordForms(boy, "boy", "boys", "boy's", "boys'");
    }

    @Test
    public void testNounForms_FEnding() {
        EnNoun thief = EnNoun.build("thief");
        EnNoun wife = EnNoun.build("wife");
        EnNoun cliff = EnNoun.build("cliff");

        assertWordForms(thief, "thief", "thieves", "thief's", "thieves'");
        assertWordForms(wife, "wife", "wives", "wife's", "wives'");
        assertWordForms(cliff, "cliff", "cliffs", "cliff's", "cliffs'");
    }

    @Test
    public void testNounForms_Irregular() {
        EnNoun man = buildIrregularNoun("man", "men");
        EnNoun hero = buildIrregularNoun("hero", "heroes");

        assertFalse(man.isRegular());
        assertFalse(man.isProperNoun());
        assertWordForms(man, "man", "men", "man's", "men's");
        assertWordForms(hero, "hero", "heroes", "hero's", "heroes'");
    }

    @Test
    public void testAlternativeForms_NounHasAlternativeForm() {
        EnNoun realization = getNounWithAlternativeForm("realization", "realisation");

        assertWordFormsFull(realization,
                "realization/realisation",
                "realizations/realisations",
                "realization's/realisation's",
                "realizations'/realisations'");
    }

    @Test
    public void testAlternativeForm_NounDoesNotHaveAlternativeForm() {
        EnNoun house = EnNoun.build("house");

        assertWordFormsFull(house, "house", "houses", "house's", "houses'");
    }

    @Test
    public void testProperNoun() {
        EnNoun john = buildProperNoun("John");
        EnNoun thomas = buildProperNoun("Thomas");

        String[] johnDeclensions = {"John", "John's"};

        assertTrue(john.isProperNoun());
        assertTrue(john.isRegular());
        assertWordForms(john, johnDeclensions);
        assertWordFormsFull(john, johnDeclensions);
        assertWordForms(thomas, "Thomas", "Thomas's");
        assertWordFormsFull(thomas, "Thomas", "Thomas's/Thomas'");
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
        EnNoun noun = EnNoun.build("abc");

        assertEquals(noun.getMainForm(), noun.getForm(EnVerbFormName.MAIN_FORM));
    }

    @Test
    public void testGetTranscription() {
        EnNoun house = EnNoun.builder("house")
                .transcription("haʊs")
                .build();

        assertEquals("[haʊs]", house.getTranscription());
    }
}
