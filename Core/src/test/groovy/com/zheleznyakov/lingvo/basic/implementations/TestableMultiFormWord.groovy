package com.zheleznyakov.lingvo.basic.implementations

import com.zheleznyakov.lingvo.basic.words.Language
import com.zheleznyakov.lingvo.basic.words.MultiFormWord
import com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech

class TestableMultiFormWord implements MultiFormWord {
    private final String mainForm;

    TestableMultiFormWord(String mainForm) {
        this.mainForm = mainForm;
    }

    @Override
    Language getLanguage() {
        return null
    }

    @Override
    PartOfSpeech getPartOfSpeech() {
        return null
    }

    @Override
    String getMainForm() {
        return mainForm
    }

    @Override
    Map<FakeFormName, String> getForms() {
        return getForms([:], FakeFormName.values())
    }
}