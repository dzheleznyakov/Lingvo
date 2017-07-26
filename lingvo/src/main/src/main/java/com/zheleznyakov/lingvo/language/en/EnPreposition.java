package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Preposition;

public class EnPreposition extends EnWord implements Preposition {

    EnPreposition(String mainForm) {
        super(mainForm);
    }

    public EnPreposition(Builder builder) {
        super(builder.mainForm);
        transcription = builder.transcription;
    }

    public static EnPreposition build(String mainForm) {
        return new EnPreposition(mainForm);
    }

    public static Builder builder(String mainForm) {
        return new Builder(mainForm);
    }

    public static class Builder {
        private String mainForm;
        private String transcription;

        public Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        public Builder transcription(String transcription) {
            this.transcription = transcription;
            return this;
        }

        public EnPreposition build() {
            return new EnPreposition(this);
        }
    }

}
