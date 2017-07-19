package com.zheleznyakov.lingvo.language.en;

import java.util.ArrayList;
import java.util.Arrays;
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
        irregularForms = builder.irregularForms;
        regular = builder.regular;
    }

    public boolean isRegular() {
        return regular;
    }

    public String[] getForms() {
        List<String> forms = new ArrayList<>();
        Arrays.stream(Form.values()).forEach(form -> {
            if (form == Form.PAST_PARTICIPLE && irregularForms.get(Form.PAST_PARTICIPLE) == null) {
                return;
            } else {
                forms.add(form.standardConverter.apply(mainForm));
            }
        });
        return (String[]) forms.toArray();
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
        MAIN_FORM(Function.identity()),
        PRESENT_SECOND_SINGULAR(EnWord::appendSEnding),
        GERUND(EnVerb::appendIngEnding),
        PAST(EnVerb::appendEdEnding),
        PAST_PARTICIPLE(EnVerb::appendEdEnding),;

        final Function<String, String> standardConverter;

        Form(Function<String, String> standardConverter) {
            this.standardConverter = standardConverter;
        }
    }

}
