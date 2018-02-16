package com.zheleznyakov.lingvo.basic.words;

import java.io.Serializable;
import java.util.Map;

import com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech;

public interface GrammaticalWord extends Serializable {

    Language getLanguage();

    PartOfSpeech getPartOfSpeech();

    String getMainForm();

    <E extends FormName> Map<E, String> getForms();

}
