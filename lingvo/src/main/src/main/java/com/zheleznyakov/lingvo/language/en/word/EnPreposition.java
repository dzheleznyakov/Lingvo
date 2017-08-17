package com.zheleznyakov.lingvo.language.en.word;

import com.zheleznyakov.lingvo.basic.Preposition;
import com.zheleznyakov.lingvo.language.en.EnWord;

public class EnPreposition extends EnWord implements Preposition {

    public EnPreposition(Builder builder) {
        super(builder);
    }

    public static EnPreposition build(String mainForm) {
        return new EnPreposition(EnPreposition.builder(mainForm));
    }

    public static Builder builder(String mainForm) {
        return new Builder(mainForm);
    }

    public static class Builder extends EnWord.Builder<EnPreposition> {
        public Builder(String mainForm) {
            super(mainForm);
        }

        @Override
        public EnPreposition build() {
            return new EnPreposition(this);
        }
    }

}
