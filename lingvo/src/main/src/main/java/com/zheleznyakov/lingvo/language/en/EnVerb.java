package com.zheleznyakov.lingvo.language.en;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.zheleznyakov.lingvo.basic.MultiFormWord;
import com.zheleznyakov.lingvo.basic.Verb;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.basic.util.WordFormatter;
import com.zheleznyakov.lingvo.basic.FormName;

public class EnVerb extends EnWord implements Verb, MultiFormWord {

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

    @Override
    public String getForm(FormName formName) {
        return getForm(formName, irregularForms);
    }

    private EnVerb(Builder builder) {
        super(builder.mainForm);
        alternativeForm = builder.alternativeForm;
        irregularForms = builder.irregularForms == null
                ? Word.EMPTY_IRREGULAR_FORMS
                : builder.irregularForms;
    }

    public static Builder builder(String mainForm) {
        return new Builder(mainForm);
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

    public enum EnVerbFormName implements FormName {
        MAIN_FORM             (Function.identity(),               null),
        PRESENT_FIRST_SINGULAR(MAIN_FORM.standardConverter,       MAIN_FORM),
        PRESENT_THIRD_SINGULAR(EnSpellingHelper::appendSEnding,   null),
        PRESENT_PLURAL        (MAIN_FORM.standardConverter,       MAIN_FORM),
        GERUND                (EnSpellingHelper::appendIngEnding, null),
        PAST_SINGLE           (EnSpellingHelper::appendEdEnding,  null),
        PAST_PLURAL           (PAST_SINGLE.standardConverter,     PAST_SINGLE),
        PAST_PARTICIPLE       (PAST_SINGLE.standardConverter,     PAST_SINGLE),;

        private final Function<String, String> standardConverter;
        private final EnVerbFormName root;

        EnVerbFormName(Function<String, String> standardConverter, EnVerbFormName root) {
            this.standardConverter = standardConverter;
            this.root = root;
        }

        @Override
        public boolean isMandatory() {
            return root == null;
        }

        @Override
        public Function<String, String> getStandardConverter() {
            return standardConverter;
        }

        @Override
        public EnVerbFormName getRoot() {
            return root;
        }
    }

}
