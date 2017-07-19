package com.zheleznyakov.lingvo.language.en;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.zheleznyakov.lingvo.basic.Verb;

public class EnVerb extends EnWord implements Verb {

    private final boolean regular;
    private final Map<Form, String> irregularForms;

    private EnVerb(Builder builder) {
        super(builder.mainForm);
        irregularForms = builder.irregularForms == null
                ? new HashMap<>()
                : builder.irregularForms;
        regular = builder.regular;
    }

    public boolean isRegular() {
        return regular;
    }

    public String[] getForms() {
        List<String> forms = new ArrayList<>();
        for (Form form : Form.values())
            appendFormToList(form, forms);
        return forms.toArray(new String[forms.size()]);
    }

    private void appendFormToList(Form form, List<String> forms) {
        String irregularForm = irregularForms.get(form);
        if (!form.isMandatory() && irregularForm == null)
            return;
        else if (irregularForm != null)
            forms.add(irregularForm);
        else
            forms.add(form.standardConverter.apply(mainForm));
    }

    private static String appendEdEnding(String form) {
        if (endsInE(form)) {
            return form.substring(0, form.length() - 1) + "ed";
        } else if (endsInVowelY(form)) {
            return form.substring(0, form.length() - 1) + "ied";
        } else {
            return form + "ed";
        }
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
        private boolean regular = true;
        private Map<Form, String> irregularForms;

        public Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        public Builder irregularForm(Form verbForm, String form) {
            if (irregularForms == null) {
                irregularForms = new HashMap<>();
            }
            irregularForms.put(verbForm, form);
            regular = false;
            return this;
        }

        public EnVerb build() {
            return new EnVerb(this);
        }
    }

    public enum Form {
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

        Form(boolean mandatory, Function<String, String> standardConverter) {
            this.mandatory = mandatory;
            this.standardConverter = standardConverter;
        }

        public boolean isMandatory() {
            return mandatory;
        }
    }

}
