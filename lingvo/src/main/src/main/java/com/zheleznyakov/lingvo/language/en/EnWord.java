package com.zheleznyakov.lingvo.language.en;

import java.util.Set;

import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.basic.Word;
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

    @Override
    public Language getLanguage() {
        return Language.ENGLISH;
    }

    protected static String[] joinForms(String[] mainDeclensions, String[] alternativeDeclensions) {
        String[] declensionsFull = new String[mainDeclensions.length];
        for (int i = 0; i < mainDeclensions.length; i++) {
            declensionsFull[i] = mainDeclensions[i] + "/" + alternativeDeclensions[i];
        }
        return declensionsFull;
    }
}
