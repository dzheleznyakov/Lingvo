package com.zheleznyakov.lingvo.basic.implementations

import com.google.common.base.MoreObjects
import com.zheleznyakov.lingvo.basic.words.Language
import com.zheleznyakov.lingvo.basic.words.MultiFormWord
import com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech

class TestableMultiFormNoun implements MultiFormWord {
    private final String mainForm;

    TestableMultiFormNoun(String mainForm) {
        this.mainForm = mainForm;
    }

    @Override
    Language getLanguage() {
        return FakeEnglish.FIXED_LANGUAGE
    }

    @Override
    PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.NOUN
    }

    @Override
    String getMainForm() {
        return mainForm
    }

    @Override
    Map<FakeFormName, String> getForms() {
        return getForms([:], FakeFormName.values())
    }

    @Override
    String toString() {
        return MoreObjects.toStringHelper("Word")
                .add("mainForm", mainForm)
                .toString();
    }
}