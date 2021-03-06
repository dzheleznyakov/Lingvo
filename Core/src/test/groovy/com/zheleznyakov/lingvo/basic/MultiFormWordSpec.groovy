package com.zheleznyakov.lingvo.basic

import com.zheleznyakov.lingvo.implementations.TestableMultiFormNoun
import spock.lang.Specification

import static com.zheleznyakov.lingvo.implementations.FakeFormName.*

class MultiFormWordSpec extends Specification {

    def "Test getForms() method in MultiFormWord interface"() {
        given: "a multiform word"
        TestableMultiFormNoun word = ["word"]

        expect: "the word forms a returned correctly"
        word.getForms() == [(MAIN_FORM) : "worda", (MANDATORY_AND_POSSIBLE_IRREGULAR) : "wordc"]
    }

    def "Test getForm method in MultiFormWord interface"() {
        given: "a multiform word"
        TestableMultiFormNoun word = ["word"]

        expect: "the word form is returned correctly"
        word.getForm([:], NOT_MANDATORY_AND_IRREGULAR) == "wordd"
    }

}

