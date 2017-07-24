package com.zheleznyakov.lingvo.basic;

import java.util.Collections;
import java.util.Map;

import com.zheleznyakov.lingvo.basic.util.WordFormatter.FormName;
import com.zheleznyakov.lingvo.language.Language;

public interface Word {

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
