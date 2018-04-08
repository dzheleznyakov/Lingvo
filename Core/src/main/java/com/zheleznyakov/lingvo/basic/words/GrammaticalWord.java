package com.zheleznyakov.lingvo.basic.words;

import java.util.Map;

import com.zheleznyakov.lingvo.basic.persistence.PersistableMetadata;
import com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech;

@PersistableMetadata
public interface GrammaticalWord {

    Language getLanguage();

    PartOfSpeech getPartOfSpeech();

    String getMainForm();

    <E extends FormName> Map<E, String> getForms();

}
