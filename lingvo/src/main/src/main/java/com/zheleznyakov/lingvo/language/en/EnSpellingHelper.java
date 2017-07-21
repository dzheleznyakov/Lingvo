package com.zheleznyakov.lingvo.language.en;

class EnSpellingHelper {

    private EnSpellingHelper() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    static String appendSEnding(String form) {
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

    static String appendEdEnding(String form) {
        if (endsInE(form))
            return form.substring(0, form.length() - 1) + "ed";
        else if (endsInVowelY(form))
            return form.substring(0, form.length() - 1) + "ied";
        else
            return form + "ed";
    }

    static String appendIngEnding(String form) {
        if (endsInE(form)) {
            return form.substring(0, form.length() - 1) + "ing";
        } else {
            return form + "ing";
        }
    }

    static boolean endsInSibilant(String form) {
        return form.endsWith("x")
                || form.endsWith("ch")
                || form.endsWith("sh");
    }

    static boolean endsInVowelY(String form) {
        return form.endsWith("y") && !EnWord.VOWELS.contains(getSecondLastChar(form));
    }

    private static char getSecondLastChar(String form) {
        return form.charAt(form.length() - 2);
    }

    static boolean endsInSingleF(String form) {
        return form.endsWith("f") && getSecondLastChar(form) != 'f';
    }

    static boolean endsInFe(String form) {
        return form.endsWith("fe");
    }

    static boolean endsInE(String form) {
        return form.endsWith("e");
    }

    static boolean endsInS(String form) {
        return form.endsWith("s");
    }
}
