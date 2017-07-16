package com.zheleznyakov.lingvo.language.en;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.util.Noun;

public class EnNoun extends EnWord implements Noun {

    private final boolean regular;

    private EnNoun(Builder builder) {
        super(builder.mainForm);
        regular = builder.regular;
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
        if (mainForm.endsWith("s")) {
            return  new String[]{mainForm, appendSEnding(), mainForm + "'", appendSEnding() + "'"};
        } else {
            return new String[]{mainForm, appendSEnding(), mainForm + "'s", appendSEnding() + "'"};
        }
    }

    static class Builder {
        String mainForm;
        boolean regular = true;

        Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        Builder irregular() {
            regular = false;
            return this;
        }

        public EnNoun build() {
            return new EnNoun(this);
        }
    }
}
