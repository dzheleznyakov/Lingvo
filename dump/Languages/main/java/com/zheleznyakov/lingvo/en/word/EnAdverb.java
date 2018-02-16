package com.zheleznyakov.lingvo.en.word;

import com.zheleznyakov.lingvo.basic.words.parts.Adverb;
import com.zheleznyakov.lingvo.en.EnWord;

public class EnAdverb extends EnWord implements Adverb {

    private EnAdverb(Builder builder) {
        super(builder);
    }

    public static EnAdverb build(String mainForm) {
        return new EnAdverb(EnAdverb.builder(mainForm));
    }

    public static Builder builder(String mainForm) {
        return new Builder(mainForm);
    }

    public static class Builder extends EnWord.Builder<EnAdverb> {
        public Builder(String mainForm) {
            super(mainForm);
        }

        @Override
        public EnAdverb build() {
            return new EnAdverb(this);
        }
    }

}
