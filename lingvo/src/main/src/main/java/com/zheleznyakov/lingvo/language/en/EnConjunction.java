package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Conjunction;

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

    public static class Builder extends EnWord.Builder {
        public Builder(String mainForm) {
            super(mainForm);
        }

        @Override
        public EnConjunction build() {
            return new EnConjunction(this);
        }
    }

}
