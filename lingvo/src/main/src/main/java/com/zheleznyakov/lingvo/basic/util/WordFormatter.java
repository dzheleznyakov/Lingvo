package com.zheleznyakov.lingvo.basic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class WordFormatter {

    private WordFormatter() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    public static String[] getForms(String mainForm, Map<FormName, String> irregularForms, FormName[] formNames) {
        List<String> forms = new ArrayList<>();
        for (FormName formName : formNames)
            appendFormToList(irregularForms, mainForm, formName, forms);
        return forms.toArray(new String[forms.size()]);
    }

    private static void appendFormToList(Map<FormName, String> irregularForms, String mainForm, FormName formName, List<String> forms) {
        String irregularForm = irregularForms.get(formName);
        if (irregularForm != null)
            forms.add(irregularForm);
        else if (formName.isMandatory())
            forms.add(formName.getStandardConverter().apply(mainForm));
    }

    public interface FormName {
        Function<String, String> getStandardConverter();
        boolean isMandatory();
    }
}
