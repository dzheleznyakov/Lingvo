package com.zheleznyakov.lingvo.basic.implementations

import com.zheleznyakov.lingvo.basic.words.Language

class FakeEnglish implements Language {
    static final Language FIXED_LANGUAGE = new FakeEnglish()

    @Override
    String name() {
        "Fake English";
    }

    @Override
    String code() {
        "Fn";
    }

    @Override
    boolean isStringLegal(String string) {
        true;
    }
}
