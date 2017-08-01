package com.zheleznyakov.lingvo.language.en;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EnWordTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private void testIllegalMainForm(String form) {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("illegal symbol");
        EnNoun.build(form);
    }

    @Test
    public void whenAddingWordWithNonEnglishSymbolsThrow() {
        testIllegalMainForm("abcж");
    }

    @Test
    public void whenAddingWordWithNumberNumbersThrow() {
        testIllegalMainForm("ab3c");
    }

    @Test
    public void addingWordWithApostropheIsFine() {
        EnAdjective.build("abc'");
    }

    @Test
    public void addingWordWithHyphenIsFine() {
        EnAdverb.build("ab-c");
    }

    @Test
    public void addingNonCharacterSymbolOtherThanApostropheOrHyphenThrows() {
        testIllegalMainForm("abc!");
        testIllegalMainForm("abc/");
    }
}
