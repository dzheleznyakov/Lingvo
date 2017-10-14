package com.zheleznyakov.lingvo.language

import spock.lang.Specification

class LanguageSpec extends Specification {

    def "Test Language.toLowerCase() method"() {
        expect:
        Language.ENGLISH.toLowerCase() == "english"
    }

}
