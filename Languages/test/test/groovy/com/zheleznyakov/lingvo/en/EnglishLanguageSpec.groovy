package com.zheleznyakov.lingvo.en

import spock.lang.Specification

class EnglishLanguageSpec extends Specification {

    def "The name for English language is 'English'"() {
        expect:
        EnglishLanguage.get().name() == "English"
    }

}
