package com.zheleznyakov.lingvo.language.en;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.basic.Noun;

public class EnNoun extends EnWord implements Noun {

    private final boolean regular;
    private final boolean properNoun;
    private final String alternativeForm;
    private final String pluralForm;
    private String[] declensionsFullForProperNoun;

    private EnNoun(Builder builder) {
        super(builder.mainForm);
        regular = builder.regular;
        properNoun = builder.properNoun;
        alternativeForm = builder.alternativeForm;
        pluralForm = builder.pluralForm;
    }

    @Override
    public boolean isRegular() {
        return regular;
    }

    public boolean isProperNoun() {
        return properNoun;
    }

    private String appendSEnding(String form) {
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

    @Override
    @NotNull
    public String[] getDeclensions() {
        return properNoun
                ? getDeclensionsForProperNoun()
                : getDeclensions(this.mainForm, this.pluralForm);
    }

    private String[] getDeclensionsForProperNoun() {
        if (mainForm.endsWith("s")) {
            return new String[]{mainForm, mainForm + "'s"};
        } else {
            return new String[]{mainForm, makePossessive(mainForm)};
        }
    }

    private String[] getDeclensions(String mForm, String pForm) {
        String pluralForm = pForm == null ? appendSEnding(mForm) : pForm;
        return new String[]{mForm, pluralForm, makePossessive(mForm), makePossessive(pluralForm)};
    }

    private static String makePossessive(String form) {
        return form.endsWith("s") ? form + "'" : form + "'s";
    }

    @NotNull
    public String[] getDeclensionsFull() {
        return properNoun
                ? getDeclensionsFullForProperNoun()
                : getDeclensionsFullForNonProperNoun();
    }

    public String[] getDeclensionsFullForProperNoun() {
        if (mainForm.endsWith("s")) {
            String[] declensions = getDeclensionsForProperNoun();
            declensions[1] += "/" + mainForm + "'";
            return declensions;
        } else {
            return getDeclensionsForProperNoun();
        }
    }

    public String[] getDeclensionsFullForNonProperNoun() {
        return alternativeForm == null
                ? getDeclensions()
                : joinForms(getDeclensions(), getDeclensions(alternativeForm, null));
    }

    private String[] joinForms(String[] mainDeclensions, String[] alternativeDeclensions) {
        String[] declensionsFull = new String[mainDeclensions.length];
        for (int i = 0; i < mainDeclensions.length; i++) {
            declensionsFull[i] = mainDeclensions[i] + "/" + alternativeDeclensions[i];
        }
        return declensionsFull;
    }

    private boolean endsInSibilant(String form) {
        return form.endsWith("x")
                || form.endsWith("ch")
                || form.endsWith("sh");
    }

    private boolean endsInVowelY(String form) {
        return form.endsWith("y") && !VOWELS.contains(getSecondLastChar());
    }

    private char getSecondLastChar() {
        return mainForm.charAt(mainForm.length() - 2);
    }

    private boolean endsInSingleF(String form) {
        return form.endsWith("f") && getSecondLastChar() != 'f';
    }

    private boolean endsInFe(String form) {
        return form.endsWith("fe");
    }

    public static Builder builder(@NotNull String noun) {
        return new Builder(noun);
    }

    static class Builder {
        String mainForm;
        String alternativeForm;
        String pluralForm;
        boolean regular = true;
        boolean properNoun = false;

        Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        public EnNoun irregularPlural(String pluralForm) {
            this.pluralForm = pluralForm;
            regular = false;
            return build();
        }

        public EnNoun alternativeForm(String alternativeForm) {
            this.alternativeForm = alternativeForm;
            return build();
        }

        public EnNoun properNoun() {
            properNoun = true;
            return build();
        }

        public EnNoun build() {
            return new EnNoun(this);
        }
    }
}
