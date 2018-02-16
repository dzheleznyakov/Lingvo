package com.zheleznyakov.lingvo.en.word;

import com.zheleznyakov.lingvo.basic.words.parts.Adjective;
import com.zheleznyakov.lingvo.en.EnWord;

public class EnAdjective extends EnWord implements Adjective {

    public static EnAdjective build(String mainForm) {
        return new EnAdjective(EnAdjective.builder(mainForm));
    }

    private EnAdjective(Builder builder) {
        super(builder);
    }

    public static Builder builder(String mainForm) {
        return new Builder(mainForm);
    }

    public static class Builder extends EnWord.Builder<EnAdjective> {
        public Builder(String mainForm) {
            super(mainForm);
        }

        @Override
        public EnAdjective build() {
            return new EnAdjective(this);
        }
    }

}
