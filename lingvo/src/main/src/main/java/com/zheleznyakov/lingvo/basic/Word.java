package com.zheleznyakov.lingvo.basic;

import com.zheleznyakov.lingvo.language.en.Language;

public interface Word {

    String getMainForm();

    Language getLanguage();

    PartOfSpeech getPartOfSpeech();
}
