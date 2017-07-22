package com.zheleznyakov.lingvo.language.en;

import java.util.Objects;
import java.util.Set;

class EnSpellingHelper {

    private EnSpellingHelper() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    static String appendSEnding(String form) {
        if (endsIn(form, EnWord.SIBILANTS))
            return form + "es";
        else if (endsInVowelY(form))
            return cutAndAppend(form, 1, "ies");
        else if (endsInSingleF(form))
            return cutAndAppend(form, 1, "ves");
        else if (endsInFe(form))
            return cutAndAppend(form, 2, "ves");
        else
            return form + "s";
    }

    static String appendEdEnding(String form) {
        if (endsInE(form))
            return cutAndAppend(form, 1, "ed");
        else if (endsInVowelY(form))
            return cutAndAppend(form, 1, "ied");
        else if (needToDuplicateLastLetter(form))
            return form + getLastLetter(form) + "ed";
        else
            return form + "ed";
    }

    static String appendIngEnding(String form) {
        if (endsInE(form))
            return cutAndAppend(form, 1, "ing");
        else if (needToDuplicateLastLetter(form))
            return form + getLastLetter(form) + "ing";
        else
            return form + "ing";
    }

    private static String cutAndAppend(String form, int endPos, String suffix) {
        return cut(form, endPos) + suffix;
    }

    private static String cut(String form, int endPos) {
        return form.substring(0, form.length() - endPos);
    }

    private static boolean needToDuplicateLastLetter(String form) {
        return isOneSyllableAndEndsInConsonant(form) ||
                isTwoSyllablesAndEndsInApproximant(form);
    }

    private static boolean isOneSyllableAndEndsInConsonant(String form) {
        return EnWord.END_CONSONANT.contains(getLastLetter(form)) &&
                EnWord.VOWELS_STRICT.contains(getSecondLastLetter(form)) &&
                countLettersFromSet(form, EnWord.VOWELS_FULL) == 1;
    }

    private static boolean isTwoSyllablesAndEndsInApproximant(String form) {
        return EnWord.APPROXIMANTS.contains(getLastLetter(form)) &&
                EnWord.VOWELS_STRICT.contains(getSecondLastLetter(form)) &&
                countLettersFromSet(form, EnWord.VOWELS_FULL) == 2;
    }

    private static long countLettersFromSet(String form, Set<String> setOfLetters) {
        return form.chars()
            .mapToObj(i -> (char) i)
            .map(String::valueOf)
            .filter(setOfLetters::contains)
            .count();
    }

    private static String getSecondLastLetter(String form) {
        return String.valueOf(getCharFromEnd(form, 1));
    }

    private static String getLastLetter(String form) {
        return String.valueOf(getCharFromEnd(form, 0));
    }

    private static char getCharFromEnd(String form, int position) {
        return form.charAt(form.length() - 1 - position);
    }

    private static boolean endsIn(String form, Set<String> letters) {
        return letters.stream()
                .anyMatch(form::endsWith);
    }

    static boolean endsInVowelY(String form) {
        String secondLastLetter = getSecondLastLetter(form);
        return form.endsWith("y") && !EnWord.VOWELS_STRICT.contains(secondLastLetter);
    }

    private static boolean endsInSingleF(String form) {
        return form.endsWith("f") && !Objects.equals(getSecondLastLetter(form), "f");
    }

    private static boolean endsInFe(String form) {
        return form.endsWith("fe");
    }

    private static boolean endsInE(String form) {
        return form.endsWith("e");
    }

    static boolean endsInS(String form) {
        return form.endsWith("s");
    }
}
