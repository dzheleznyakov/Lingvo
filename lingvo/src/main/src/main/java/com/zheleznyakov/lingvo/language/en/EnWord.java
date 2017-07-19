package com.zheleznyakov.lingvo.language.en;

import java.util.Set;

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

    protected static String appendSEnding(String form) {
        if (endsInSibilant(form) || form.endsWith("s")) {
            return form + "es";
        } else if (endsInVowelY(form)) {
            return form.substring(0, form.length() - 1) + "ies";
        } else if (endsInSingleF(form)) {
            return form.substring(0, form.length() - 1) + "ves";
        } else if (endsInFe(form)) {
            return form.substring(0, form.length() - 2) + "ves";
        } else {
            return form + "s";
        }
    }

    protected static boolean endsInSibilant(String form) {
        return form.endsWith("x")
                || form.endsWith("ch")
                || form.endsWith("sh");
    }

    protected static boolean endsInVowelY(String form) {
        return form.endsWith("y") && !VOWELS.contains(getSecondLastChar(form));
    }

    protected static char getSecondLastChar(String form) {
        return form.charAt(form.length() - 2);
    }

    protected static boolean endsInSingleF(String form) {
        return form.endsWith("f") && getSecondLastChar(form) != 'f';
    }

    protected static boolean endsInFe(String form) {
        return form.endsWith("fe");
    }
}
