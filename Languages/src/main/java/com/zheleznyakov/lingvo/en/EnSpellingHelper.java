package com.zheleznyakov.lingvo.en;

import com.google.common.collect.ImmutableSet;

public class EnSpellingHelper {

    private EnSpellingHelper() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    static String appendSEnding(String form) {
        if (endsInOneOf(form, EnglishLanguage.SIBILANTS))
            return form + "es";
        else if (endsInSingleF(form))
            return cutAndAppend(form, 1, "ves");
        else if (endsInFe(form))
            return cutAndAppend(form, 2, "ves");
        else
            return form + "s";
    }

    private static boolean endsInFe(String form) {
        return form.endsWith("fe");
    }

    private static boolean endsInOneOf(String form, ImmutableSet<String> letters) {
        return letters.stream()
                .anyMatch(form::endsWith);
    }

    private static boolean endsInSingleF(String form) {
        int length = form.length();
        return form.endsWith("f") && form.charAt(length - 2) != 'f';
    }

    private static String cutAndAppend(String form, int endPos, String suffix) {
        return cut(form, endPos) + suffix;
    }

    private static String cut(String form, int endPos) {
        return form.substring(0, form.length() - endPos);
    }
}
