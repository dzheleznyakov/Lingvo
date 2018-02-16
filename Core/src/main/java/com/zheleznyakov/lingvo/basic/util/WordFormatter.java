package com.zheleznyakov.lingvo.basic.util;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.ImmutableMap;
import com.zheleznyakov.lingvo.basic.FormName;

public class WordFormatter {

    private WordFormatter() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    public static <F extends FormName> ImmutableMap<F, String> getForms(String mainForm, Map<F, String> irregularForms, F[] formNames) {
        return Arrays.stream(formNames)
                .filter(FormName::isMandatory)
                .collect(ImmutableMap.toImmutableMap(Function.identity(), formName -> getForm(mainForm, irregularForms, formName)));
    }

    public static <F extends FormName> String getForm(String mainForm, Map<F, String> irregularForms, F formName) {
        return irregularForms.getOrDefault(formName, getConverter(formName).apply(mainForm));
    }

    private static Function<String, String> getConverter(FormName formName) {
        return formName.getRoot().getStandardConverter();
    }
}
