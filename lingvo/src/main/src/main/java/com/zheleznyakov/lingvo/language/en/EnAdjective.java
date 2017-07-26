package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Adjective;

public class EnAdjective extends EnWord implements Adjective {

    private EnAdjective(String mainForm) {
        super(mainForm);
    }

    public static EnAdjective build(String adjString) {
        return new EnAdjective(adjString);
    }

    public EnAdjective(Builder builder) {
        super(builder.mainForm);
        transcription = builder.transcription;
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

        public EnAdjective build() {
            return new EnAdjective(this);
        }
    }

}
