package com.zheleznyakov.lingvo.basic;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import com.zheleznyakov.lingvo.basic.parts.PartOfSpeech;

public interface Word extends Serializable {

    Map<FormName, String> EMPTY_IRREGULAR_FORMS = Collections.EMPTY_MAP;

    Language getLanguage();

    PartOfSpeech getPartOfSpeech();

    String getMainForm();

    <E extends FormName> Map<E, String> getForms();

}
