package com.zheleznyakov.lingvo.language.en;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.util.ChangeType;
import com.zheleznyakov.lingvo.util.Noun;

public class EnNoun implements EnWord, Noun {

    private final String mainForm;
    private final ChangeType changeType;

    private EnNoun(Builder builder) {
        mainForm = builder.mainForm;
        changeType = builder.changeType;
    }

    @NotNull
    public static Builder builder(@NotNull String noun) {
        return new Builder(noun);
    }

    @Override
    public String getMainForm() {
        return mainForm;
    }

    @Override
    public ChangeType getChangeType() {
        return changeType;
    }

    @Override
    public String[] getDeclensions() {
        char lastChar = mainForm.charAt(mainForm.length() - 1);
        if (lastChar == 'x') {
            return  new String[]{mainForm, mainForm + "es", mainForm + "'s", mainForm + "es'"};
        } else {
            return new String[]{mainForm, mainForm + "s", mainForm + "'s", mainForm + "s'"};
        }
    }

    static class Builder {
        String mainForm;
        ChangeType changeType = ChangeType.REG;

        Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        Builder irregular() {
            changeType = ChangeType.IRR;
            return this;
        }

        public EnNoun build() {
            return new EnNoun(this);
        }
    }
}
