package com.zheleznyakov.lingvo.language

import com.zheleznyakov.lingvo.basic.Language
import spock.lang.Specification

class LanguageSpec extends Specification {

    def "Test Language.toLowerCase() method"() {
        expect:
        Language.ENGLISH.toLowerCase() == "english"
    }

}
