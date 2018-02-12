package com.zheleznyakov.lingvo.basic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zheleznyakov.lingvo.basic.FormName;

public class WordFormatter {

    private WordFormatter() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    public static <F extends FormName> String getForm(String mainForm, Map<F, String> irregularForms, F formName) {
        return irregularForms.getOrDefault(formName, formName.getStandardConverter().apply(mainForm));
    }

    public static <F extends FormName> String[] getForms(String mainForm, Map<F, String> irregularForms, F[] formNames) {
        List<String> forms = new ArrayList<>();
        for (F formName : formNames)
            appendFormToList(mainForm, irregularForms, formName, forms);
        return forms.toArray(new String[forms.size()]);
    }

    private static <F extends FormName> void appendFormToList(String mainForm, Map<F, String> irregularForms, F formName, List<String> forms) {
        if (formName.isMandatory())
            forms.add(getForm(mainForm, irregularForms, formName));
    }

}
