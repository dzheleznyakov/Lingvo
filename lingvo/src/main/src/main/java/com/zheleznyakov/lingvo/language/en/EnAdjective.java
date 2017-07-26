package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Adjective;

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

    public static class Builder extends EnWord.Builder {

        public Builder(String mainForm) {
            super(mainForm);
        }

        @Override
        public EnAdjective build() {
            return new EnAdjective(this);
        }
    }

}
