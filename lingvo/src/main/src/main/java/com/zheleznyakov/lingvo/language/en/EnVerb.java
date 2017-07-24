package com.zheleznyakov.lingvo.language.en;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.zheleznyakov.lingvo.basic.Verb;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.basic.util.WordFormatter;
import com.zheleznyakov.lingvo.basic.util.WordFormatter.FormName;

public class EnVerb extends EnWord implements Verb {

    private final String alternativeForm;
    private final Map<FormName, String> irregularForms;

    @Override
    public boolean isRegular() {
        return irregularForms.isEmpty();
    }

    @Override
    public String[] getForms() {
        return WordFormatter.getForms(mainForm, irregularForms, EnVerbFormName.values());
    }

    @Override
    public String[] getFormsFull() {
        return alternativeForm == null
                ? getForms()
                : joinForms(getForms(), WordFormatter.getForms(alternativeForm, irregularForms, EnVerbFormName.values()));
    }

    public static Builder builder(String mainForm) {
        return new Builder(mainForm);
    }

    private EnVerb(Builder builder) {
        super(builder.mainForm);
        alternativeForm = builder.alternativeForm;
        irregularForms = builder.irregularForms == null
                ? Word.EMPTY_IRREGULAR_FORMS
                : builder.irregularForms;
    }

    public static class Builder {
        private String mainForm;
        private String alternativeForm;
        private Map<FormName, String> irregularForms;

        private Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        public Builder irregularForm(EnVerbFormName verbEnVerbFormName, String form) {
            if (irregularForms == null)
                irregularForms = new HashMap<>();
            irregularForms.put(verbEnVerbFormName, form);
            return this;
        }

        public EnVerb alternativeForm(String alternativeForm) {
            this.alternativeForm = alternativeForm;
            return build();
        }

        public EnVerb build() {
            return new EnVerb(this);
        }
    }

    public enum EnVerbFormName implements WordFormatter.FormName {
        MAIN_FORM             (true,  Function.identity()),
        PRESENT_FIRST_SINGULAR(false, MAIN_FORM.standardConverter),
        PRESENT_THIRD_SINGULAR(true,  EnSpellingHelper::appendSEnding),
        PRESENT_PLURAL        (false, MAIN_FORM.standardConverter),
        GERUND                (true,  EnSpellingHelper::appendIngEnding),
        PAST_SINGLE           (true,  EnSpellingHelper::appendEdEnding),
        PAST_PLURAL           (false, PAST_SINGLE.standardConverter),
        PAST_PARTICIPLE       (false, PAST_SINGLE.standardConverter),;

        private final boolean mandatory;
        private final Function<String, String> standardConverter;

        EnVerbFormName(boolean mandatory, Function<String, String> standardConverter) {
            this.mandatory = mandatory;
            this.standardConverter = standardConverter;
        }

        @Override
        public boolean isMandatory() {
            return mandatory;
        }

        @Override
        public Function<String, String> getStandardConverter() {
            return standardConverter;
        }
    }

}
