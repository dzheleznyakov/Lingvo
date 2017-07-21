package com.zheleznyakov.lingvo.language.en;

import static com.zheleznyakov.lingvo.language.en.EnSpellingHelper.appendSEnding;
import static com.zheleznyakov.lingvo.language.en.EnSpellingHelper.endsInS;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.basic.Noun;

public class EnNoun extends EnWord implements Noun {

    private final boolean regular;
    private final boolean properNoun;
    private final String alternativeForm;
    private final String pluralForm;

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

    @Override
    public boolean isProperNoun() {
        return properNoun;
    }

    @Override
    @NotNull
    public String[] getForms() {
        return properNoun
                ? getFormsForProperNoun()
                : getForms(this.mainForm, this.pluralForm);
    }

    private String[] getFormsForProperNoun() {
        return new String[]{mainForm, mainForm + "'s"};
    }

    private String[] getForms(String mForm, String pForm) {
        String pluralForm = pForm == null ? appendSEnding(mForm) : pForm;
        return new String[]{mForm, pluralForm, makePossessive(mForm), makePossessive(pluralForm)};
    }

    private static String makePossessive(String form) {
        return endsInS(form) ? form + "'" : form + "'s";
    }

    @Override
    @NotNull
    public String[] getFormsFull() {
        return properNoun
                ? getFormsFullForProperNoun()
                : getFormsFullForNonProperNoun();
    }

    private String[] getFormsFullForProperNoun() {
        if (endsInS(mainForm)) {
            String[] declensions = getFormsForProperNoun();
            declensions[1] += "/" + mainForm + "'";
            return declensions;
        } else {
            return getFormsForProperNoun();
        }
    }

    private String[] getFormsFullForNonProperNoun() {
        return alternativeForm == null
                ? getForms()
                : joinForms(getForms(), getForms(alternativeForm, null));
    }

    public static Builder builder(@NotNull String noun) {
        return new Builder(noun);
    }

    public static class Builder {
        private String mainForm;
        private String alternativeForm;
        private String pluralForm;
        private boolean regular = true;
        private boolean properNoun = false;

        private Builder(String mainForm) {
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
