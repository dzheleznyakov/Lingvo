package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Verb;

public class EnVerb extends EnWord implements Verb {
    public static Builder builder(String mainForm) {
        return new Builder(mainForm);
    }

    private EnVerb(Builder builder) {
        super(builder.mainForm);
    }

    public static class Builder {
        private String mainForm;

        public Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        public EnVerb build() {
            return new EnVerb(this);
        }
    }
}
