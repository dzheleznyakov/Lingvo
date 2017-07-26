package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Adverb;

public class EnAdverb extends EnWord implements Adverb {

    private EnAdverb(String mainForm) {
        super(mainForm);
    }

    public EnAdverb(Builder builder) {
        super(builder.mainForm);
        transcription = builder.transcription;
    }

    public static EnAdverb build(String mainForm) {
        return new EnAdverb(mainForm);
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

        public EnAdverb build() {
            return new EnAdverb(this);
        }
    }

}
