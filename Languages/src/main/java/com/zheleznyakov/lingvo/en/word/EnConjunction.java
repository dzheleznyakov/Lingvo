package com.zheleznyakov.lingvo.en.word;

import com.zheleznyakov.lingvo.en.EnWord;

import com.zheleznyakov.lingvo.basic.parts.Conjunction;

public class EnConjunction extends EnWord implements Conjunction {

    public EnConjunction(Builder builder) {
        super(builder);
    }

    public static EnConjunction build(String mainForm) {
        return new EnConjunction(EnConjunction.builder(mainForm));
    }

    public static Builder builder(String mainForm) {
        return new Builder(mainForm);
    }

    public static class Builder extends EnWord.Builder<EnConjunction> {
        public Builder(String mainForm) {
            super(mainForm);
        }

        @Override
        public EnConjunction build() {
            return new EnConjunction(this);
        }
    }

}
