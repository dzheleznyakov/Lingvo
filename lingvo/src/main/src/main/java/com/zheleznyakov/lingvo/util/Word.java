package com.zheleznyakov.lingvo.util;

import com.zheleznyakov.lingvo.language.en.Language;

public interface Word {

    String getMainForm();

    Language getLanguage();

    PartOfSpeech getPartOfSpeech();
}
