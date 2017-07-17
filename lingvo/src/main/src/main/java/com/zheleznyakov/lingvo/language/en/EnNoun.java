package com.zheleznyakov.lingvo.language.en;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.basic.Noun;

public class EnNoun extends EnWord implements Noun {

    private final boolean regular;
    private final String plural;

    private EnNoun(Builder builder) {
        super(builder.mainForm);
        regular = builder.regular;
        plural = regular ? appendSEnding() : builder.plural;
    }

    private String appendSEnding() {
        if (endsInSibilant() || mainForm.endsWith("s")) {
            return mainForm + "es";
        } else if (endsWithVowelY()) {
            return mainForm.substring(0, mainForm.length() - 1) + "ies";
        } else if (endsInSingleF()) {
            return mainForm.substring(0, mainForm.length() - 1) + "ves";
        } else if (endsInFe()) {
            return mainForm.substring(0, mainForm.length() - 2) + "ves";
        } else {
            return mainForm + "s";
        }
    }

    @NotNull
    public static Builder builder(@NotNull String noun) {
        return new Builder(noun);
    }

    @Override
    public boolean isRegular() {
        return regular;
    }

    @Override
    public String[] getDeclensions() {
        return new String[]{mainForm, plural, makePossessive(mainForm), makePossessive(plural)};
    }

    private static String makePossessive(String form) {
        return form.endsWith("s") ? form + "'" : form + "'s";
    }

    private boolean endsInSibilant() {
        return mainForm.endsWith("x")
                || mainForm.endsWith("ch")
                || mainForm.endsWith("sh");
    }

    private boolean endsWithVowelY() {
        return mainForm.endsWith("y") && !VOWELS.contains(getSecondLastChar());
    }

    private char getSecondLastChar() {
        return mainForm.charAt(mainForm.length() - 2);
    }

    private boolean endsInSingleF() {
        return mainForm.endsWith("f") && getSecondLastChar() != 'f';
    }

    private boolean endsInFe() {
        return mainForm.endsWith("fe");
    }

    static class Builder {
        String mainForm;
        String plural;
        boolean regular = true;

        Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        public Builder irregularPlural(String plural) {
            this.plural = plural;
            regular = false;
            return this;
        }

        public EnNoun build() {
            return new EnNoun(this);
        }
    }
}
