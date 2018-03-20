package com.zheleznyakov.lingvo.implementations

import com.zheleznyakov.lingvo.basic.words.Language

class FakeEnglish extends Language {
    static final Language FIXED_LANGUAGE = new FakeEnglish("Fake English", "Fn")

    private String name;
    private String code;

    FakeEnglish(String name, String code) {
        this.name = name
        this.code = code
    }

    @Override
    String name() {
        name
    }

    @Override
    String code() {
        code
    }

    @Override
    boolean isStringLegal(String string) {
        true
    }

}