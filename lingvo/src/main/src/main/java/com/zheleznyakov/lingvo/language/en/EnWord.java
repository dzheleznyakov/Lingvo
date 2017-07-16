package com.zheleznyakov.lingvo.language.en;

import java.util.Set;

import com.zheleznyakov.lingvo.util.Word;
import com.google.common.collect.ImmutableSet;

public abstract class EnWord implements Word {

    public static final Set<Character> VOWELS = ImmutableSet.of('a', 'o', 'e', 'u', 'i');

    protected final String mainForm;

    protected EnWord(String mainForm) {
        this.mainForm = mainForm;
    }

    @Override
    public String getMainForm() {
        return mainForm;
    }

    public Language getLanguage() {
        return Language.ENGLISH;
    }
}
