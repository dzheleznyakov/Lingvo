package com.zheleznyakov.lingvo.language.en;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EnWordTest {
    static int count = 0;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private void testIllegalMainForm(String form) {
        count++;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("illegal symbols");

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
    public void addingWordWithApostrophIsFine() {
        EnAdjective.build("abc'");
    }

    @Test
    public void addingWordWithHyphenIsFine() {
        EnAdverb.build("ab-c");
    }

    @Test
    public void testIllegalMainForm() {
        for (int i = 0; i < 2000; i++) {
            testIllegalMainForm("abc" + ((char)i));
        }
        System.out.println(count);
    }
}
