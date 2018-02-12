package com.zheleznyakov.lingvo.basic;

import java.util.Map;

public interface MultiFormWord extends Word {

    <F extends FormName> String getForm(F formName);

    default <F extends FormName> String getForm(F formName, Map<F, String> irregularForms) {
        if (irregularForms.containsKey(formName))
            return irregularForms.get(formName);
        else if (formName.getRoot() != null)
            return getForm((F) formName.getRoot(), irregularForms);
        else
            return formName.getStandardConverter().apply(getMainForm());
    }
}
