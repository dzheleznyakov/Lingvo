package com.zheleznyakov.lingvo.en

import com.zheleznyakov.lingvo.basic.Language
import spock.lang.Specification

class EnglishLanguageSpec extends Specification {
    private static final Language ENGLISH = EnglishLanguage.get()

    def "Test English Language basic properties"() {
        expect: "the English name and code are returned correctly"
        ENGLISH.name() == "English"
        ENGLISH.code() == "En"
    }

}
