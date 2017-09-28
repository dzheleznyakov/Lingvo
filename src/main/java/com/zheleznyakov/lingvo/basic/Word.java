package com.zheleznyakov.lingvo.basic;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import com.zheleznyakov.lingvo.language.Language;

public interface Word extends Serializable {

    Map<FormName, String> EMPTY_IRREGULAR_FORMS = Collections.EMPTY_MAP;

    Language getLanguage();

    PartOfSpeech getPartOfSpeech();

    String getMainForm();

    default String[] getForms() {
        return new String[]{getMainForm()};
    }

    default String[] getFormsFull() {
        return getForms();
    };
}
