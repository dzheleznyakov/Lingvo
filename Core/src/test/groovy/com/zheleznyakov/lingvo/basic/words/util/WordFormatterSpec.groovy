package com.zheleznyakov.lingvo.basic.words.util

import com.zheleznyakov.lingvo.basic.implementations.FakeFormName
import spock.lang.Specification

import static com.zheleznyakov.lingvo.basic.implementations.FakeFormName.MANDATORY_AND_POSSIBLE_IRREGULAR

class WordFormatterSpec extends Specification {

    def "WordFormatter returns only mandatory forms"() {
        given: "the main form of a word"
        String mainForm = "word"

        and: "expected forms"
        def expectedForms = [
                (FakeFormName.MAIN_FORM)                       : "worda",
                (MANDATORY_AND_POSSIBLE_IRREGULAR): "wordc"]

        when: "getting forms from WordFormatter"
        def actualForms = WordFormatter.getForms(mainForm, [:], FakeFormName.values())

        then: "only mandatory forms are returned"
        actualForms == expectedForms
    }

    def "WordFormatter returns mandatory irregular forms correctly if such exist"() {
        given: "the main form of a word"
        String mainForm = "word"

        and: "irregular forms"
        def irregularForms = [(MANDATORY_AND_POSSIBLE_IRREGULAR) : "wordcc"]

        when: "getting forms from WordFormatter"
        def actualForms = WordFormatter.getForms(mainForm, irregularForms, FakeFormName.values())

        then: "irregular form is returned correctly"
        actualForms.containsValue("wordcc")
        !actualForms.containsValue("wordc")
    }

    def "Test getting a specific regular word form"() {
        given: "the main form of a word"
        String mainForm  = "word"

        when: "getting a form with WordFormatter"
        def actualForm = WordFormatter.getForm(mainForm, [:], MANDATORY_AND_POSSIBLE_IRREGULAR)

        then: "the form is returned correctly"
        actualForm == "wordc"
    }

    def "Test getting a specific irregular word form"() {
        given: "the main form of a word"
        String mainForm  = "word"

        and: "irregular forms"
        def irregularForms = [(MANDATORY_AND_POSSIBLE_IRREGULAR) : "wordcc"]

        when: "getting a form with WordFormatter"
        def actualForm = WordFormatter.getForm(mainForm, irregularForms, MANDATORY_AND_POSSIBLE_IRREGULAR)

        then: "the form is returned correctly"
        actualForm == "wordcc"
    }
}