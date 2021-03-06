package com.zheleznyakov.lingvo.en;

import com.zheleznyakov.lingvo.basic.Language;

public class EnglishLanguage implements Language {
    private static EnglishLanguage INSTANCE;

    private EnglishLanguage() {}

    public static EnglishLanguage get() {
        if (INSTANCE == null)
            INSTANCE = new EnglishLanguage();
        return INSTANCE;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String code() {
        return null;
    }

    @Override
    public boolean isStringLegitimate(String string) {
        return false;
    }
}
