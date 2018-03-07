package com.zheleznyakov.lingvo.basic.implementations

import com.zheleznyakov.lingvo.basic.persistence.entities.ObjectPersistenceEntity
import com.zheleznyakov.lingvo.basic.persistence.entities.PersistenceEntity
import com.zheleznyakov.lingvo.basic.persistence.converters.Converter
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

class FakeEnglishConverter implements Converter<FakeEnglish> {

    @Override
    PersistenceEntity convert(FakeEnglish object) {
        return [FakeEnglish.class] as ObjectPersistenceEntity
    }

}