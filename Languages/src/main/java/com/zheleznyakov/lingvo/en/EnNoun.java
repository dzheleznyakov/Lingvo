package com.zheleznyakov.lingvo.en;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.zheleznyakov.lingvo.basic.FormName;
import com.zheleznyakov.lingvo.basic.parts.Noun;
import com.zheleznyakov.lingvo.basic.util.WordFormatter;

public class EnNoun extends EnWord implements Noun {
    private final Map<EnNounFormName, String> irregularForms = new HashMap<>();

    private EnNoun(EnNounBuilder builder) {
        super(builder.mainForm);
        if (builder.plural != null) {
            irregularForms.put(EnNounFormName.NOMINATIVE_PLURAL, builder.plural);
        }
    }

    public static EnNoun build(String mainForm) {
        return builder(mainForm).build();
    }

    @Override
    public boolean isRegular() {
        return true;
    }

    @Override
    public boolean isProperNoun() {
        return false;
    }

    public String getForm(EnNounFormName formName) {
        return WordFormatter.getForm(mainForm, irregularForms, formName);
    }

    public static EnNounBuilder builder(String mainForm) {
        return new EnNounBuilder(mainForm);
    }

    public static class EnNounBuilder extends EnWord.Builder<EnNoun> {
        private String plural;

        EnNounBuilder(String mainForm) {
            super(mainForm);
        }

        public EnNounBuilder withPlural(String plural) {
            this.plural = plural;
            return this;
        }

        @Override
        public EnNoun build() {
            return new EnNoun(this);
        }
    }


    public enum  EnNounFormName implements FormName {
        NOMINATIVE_PLURAL(EnSpellingHelper::appendSEnding);

        private final Function<String, String > standardConverter;

        EnNounFormName(Function<String, String> standardConverter) {
            this.standardConverter = standardConverter;
        }

        @Override
        public boolean isMandatory() {
            return true;
        }

        @Override
        public Function<String, String> getStandardConverter() {
            return standardConverter;
        }

        @Override
        public FormName getRoot() {
            return this;
        }
    }
}
