package com.zheleznyakov.lingvo.language.en;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.zheleznyakov.lingvo.basic.Verb;

public class EnVerb extends EnWord implements Verb {

    private final boolean regular;
    private final String alternativeForm;
    private final Map<FormName, String> irregularForms;

    private EnVerb(Builder builder) {
        super(builder.mainForm);
        alternativeForm = builder.alternativeForm;
        irregularForms = builder.irregularForms == null
                ? new HashMap<>()
                : builder.irregularForms;
        regular = builder.regular;
    }

    public boolean isRegular() {
        return regular;
    }

    public String[] getForms() {
        return getForms(irregularForms, mainForm);
    }

    private static String[] getForms(Map<FormName, String> irregularForms, String mainForm) {
        List<String> forms = new ArrayList<>();
        for (FormName formName : FormName.values())
            appendFormToList(irregularForms, mainForm, formName, forms);
        return forms.toArray(new String[forms.size()]);
    }

    private static void appendFormToList(Map<FormName, String> irregularForms, String mainForm, FormName formName, List<String> forms) {
        String irregularForm = irregularForms.get(formName);
        if (!formName.isMandatory() && irregularForm == null)
            return;
        else if (irregularForm != null)
            forms.add(irregularForm);
        else
            forms.add(formName.standardConverter.apply(mainForm));
    }

    public String[] getFormsFull() {
        return alternativeForm == null
                ? getForms()
                : joinForms(getForms(), getForms(irregularForms, alternativeForm));
    }

    private static String appendEdEnding(String form) {
        if (endsInE(form))
            return form.substring(0, form.length() - 1) + "ed";
        else if (endsInVowelY(form))
            return form.substring(0, form.length() - 1) + "ied";
        else
            return form + "ed";
    }

    private static String appendIngEnding(String form) {
        if (endsInE(form)) {
            return form.substring(0, form.length() - 1) + "ing";
        } else {
            return form + "ing";
        }
    }

    private static boolean endsInE(String form) {
        return form.endsWith("e");
    }

    public static Builder builder(String mainForm) {
        return new Builder(mainForm);
    }

    public static class Builder {
        private String mainForm;
        private String alternativeForm;
        private boolean regular = true;
        private Map<FormName, String> irregularForms;

        private Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        public Builder irregularForm(FormName verbFormName, String form) {
            if (irregularForms == null)
                irregularForms = new HashMap<>();
            irregularForms.put(verbFormName, form);
            regular = false;
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

    public enum FormName {
        MAIN_FORM(true, Function.identity()),
        PRESENT_FIRST_SINGULAR(false, MAIN_FORM.standardConverter),
        PRESENT_THIRD_SINGULAR(true, EnWord::appendSEnding),
        PRESENT_PLURAL(false, MAIN_FORM.standardConverter),
        GERUND(true, EnVerb::appendIngEnding),
        PAST_SINGLE(true, EnVerb::appendEdEnding),
        PAST_PLURAL(false, PAST_SINGLE.standardConverter),
        PAST_PARTICIPLE(false, PAST_SINGLE.standardConverter),;

        private final boolean mandatory;
        private final Function<String, String> standardConverter;

        FormName(boolean mandatory, Function<String, String> standardConverter) {
            this.mandatory = mandatory;
            this.standardConverter = standardConverter;
        }

        public boolean isMandatory() {
            return mandatory;
        }
    }

}
