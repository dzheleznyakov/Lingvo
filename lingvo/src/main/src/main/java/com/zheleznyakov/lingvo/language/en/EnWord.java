package com.zheleznyakov.lingvo.language.en;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.Language;

public abstract class EnWord implements Word {

    public static final Set<String> VOWELS_STRICT = ImmutableSet.of("a", "o", "e", "u", "i");
    public static final Set<String> VOWELS_FULL = ImmutableSet.<String>builder()
            .addAll(VOWELS_STRICT).add("y").build();
    public static final Set<String> END_CONSONANT = ImmutableSet
            .of("b", "d", "g", "l", "m", "n", "p", "r", "s", "t", "z");
    public static final Set<String> SIBILANTS = ImmutableSet.of("x", "ch", "sh", "s");
    public static final Set<String> APPROXIMANTS = ImmutableSet.of("r", "l");

    protected final String mainForm;
    protected String transcription;

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

    public String getTranscription() {
        return "[" + transcription + "]";
    }
}
