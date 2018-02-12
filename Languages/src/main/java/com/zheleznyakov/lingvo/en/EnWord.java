package com.zheleznyakov.lingvo.en;

import com.zheleznyakov.lingvo.basic.Language;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.util.Util;

public abstract class EnWord implements Word {
    protected final String mainForm;

    protected EnWord(String mainForm) {
        Util.validateArgument(EnglishLanguage.get().isStringLegal(mainForm), "String [{}] is illegal for English");
        this.mainForm = mainForm;
    }

    @Override
    public Language getLanguage() {
        return EnglishLanguage.get();
    }

    @Override
    public String getMainForm() {
        return mainForm;
    }

    public static abstract class Builder<T extends EnWord> {
        protected String mainForm;

        protected Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        public abstract T build();
    }
}
