package com.zheleznyakov.lingvo.en;

import com.google.common.collect.ImmutableSet;
import com.zheleznyakov.lingvo.basic.words.Language;

public class EnglishLanguage extends Language {
    static final ImmutableSet<String> SIBILANTS = ImmutableSet.of(
            "x","ch", "sh", "s");

    private static EnglishLanguage INSTANCE;

    private EnglishLanguage() {}

    public static EnglishLanguage get() {
        if (INSTANCE == null)
            INSTANCE = new EnglishLanguage();
        return INSTANCE;
    }

    @Override
    public String name() {
        return "English";
    }

    @Override
    public String code() {
        return "En";
    }

    @Override
    public boolean isStringLegal(String string) {
        String letters = "[a-zA-Z]*";
        String atLeastOneLetter = "[a-zA-Z]+";
        return string.matches("'?" + atLeastOneLetter + "(-" + atLeastOneLetter +")?'?" + letters);
    }
}
