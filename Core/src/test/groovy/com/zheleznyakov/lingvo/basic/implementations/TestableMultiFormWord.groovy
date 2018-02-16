package com.zheleznyakov.lingvo.basic.implementations

import com.zheleznyakov.lingvo.basic.Language
import com.zheleznyakov.lingvo.basic.MultiFormWord
import com.zheleznyakov.lingvo.basic.parts.PartOfSpeech

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