package com.zheleznyakov.lingvo.basic.words;

import java.util.Map;

import com.zheleznyakov.lingvo.basic.words.util.WordFormatter;

public interface MultiFormWord extends GrammaticalWord {

    default <F extends FormName> String getForm(Map<F, String> irregularForms, F formName) {
        return WordFormatter.getForm(getMainForm(), irregularForms, formName);
    }

    default <F extends FormName> Map<F, String> getForms(Map<F, String> irregularForms, F[] formNames) {
        return WordFormatter.getForms(getMainForm(), irregularForms, formNames);
    }
}
