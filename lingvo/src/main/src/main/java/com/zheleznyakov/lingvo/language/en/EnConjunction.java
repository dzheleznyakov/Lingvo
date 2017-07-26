package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Conjunction;

public class EnConjunction extends EnWord implements Conjunction {

    private EnConjunction(String mainForm) {
        super(mainForm);
    }

    public EnConjunction(Builder builder) {
        super(builder.mainForm);
        transcription = builder.transcription;
    }

    public static EnConjunction build(String mainForm) {
        return new EnConjunction(mainForm);
    }

    public static Builder builder(String mainForm) {
        return new Builder(mainForm);
    }

    public static class Builder {
        private final String mainForm;
        private String transcription;

        public Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        public Builder transcription(String transcription) {
            this.transcription = transcription;
            return this;
        }

        public EnConjunction build() {
            return new EnConjunction(this);
        }
    }

}
