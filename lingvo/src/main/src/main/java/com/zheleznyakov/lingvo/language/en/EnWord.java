package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.util.Word;

public abstract class EnWord implements Word {

    protected final String mainForm;

    protected EnWord(String mainForm) {
        this.mainForm = mainForm;
    }

    @Override
    public String getMainForm() {
        return mainForm;
    }

    protected boolean endsInSibilant() {
        return mainForm.endsWith("x")
                || mainForm.endsWith("ch")
                || mainForm.endsWith("sh");
    }

    protected String appendSEnding() {
        if (endsInSibilant() || mainForm.endsWith("s")) {
            return mainForm + "es";
        } else if (mainForm.endsWith("y")) {
            return mainForm.substring(0, mainForm.length() - 1) + "ies";
        } else {
            return mainForm + "s";
        }
    }

    public Language getLanguage() {
        return Language.ENGLISH;
    }
}
