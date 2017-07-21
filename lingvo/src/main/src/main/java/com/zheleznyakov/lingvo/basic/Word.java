package com.zheleznyakov.lingvo.basic;

import com.zheleznyakov.lingvo.language.Language;

public interface Word {

    Language getLanguage();

    PartOfSpeech getPartOfSpeech();

    String getMainForm();

    String[] getForms();

    String[] getFormsFull();
}
