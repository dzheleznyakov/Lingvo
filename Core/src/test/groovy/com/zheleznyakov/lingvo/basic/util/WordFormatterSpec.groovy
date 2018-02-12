package com.zheleznyakov.lingvo.basic.util

import com.zheleznyakov.lingvo.basic.FormName
import spock.lang.Specification

import java.util.function.Function

class WordFormatterSpec extends Specification {

    def "WordFormatter returns only mandatory forms"() {
        given: "the main form of a word"
        String mainForm = "word"

        and: "expected forms"
        def expectedForms = [
                (FakeFormName.MAIN_FORM) : "worda",
                (FakeFormName.MANDATORY_AND_POSSIBLE_IRREGULAR) : "wordc"]

        when: "getting forms from WordFormatter"
        def actualForms = WordFormatter.getForms(mainForm, [:], FakeFormName.values())

        then: "only mandatory forms are returned"
        actualForms == expectedForms
    }

    def "WordFormatter returns mandatory irregular forms correctly if such exist"() {
        given: "the main form of a word"
        String mainForm = "word"

        and: "irregular forms"
        def irregularForms = [(FakeFormName.MANDATORY_AND_POSSIBLE_IRREGULAR) : "wordcc"]

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
        def actualForm = WordFormatter.getForm(mainForm, [:], FakeFormName.MANDATORY_AND_POSSIBLE_IRREGULAR)

        then: "the form is returned correctly"
        actualForm == "wordc"
    }

    def "Test getting a specific irregular word form"() {
        given: "the main form of a word"
        String mainForm  = "word"

        and: "irregular forms"
        def irregularForms = [(FakeFormName.MANDATORY_AND_POSSIBLE_IRREGULAR) : "wordcc"]

        when: "getting a form with WordFormatter"
        def actualForm = WordFormatter.getForm(mainForm, irregularForms, FakeFormName.MANDATORY_AND_POSSIBLE_IRREGULAR)

        then: "the form is returned correctly"
        actualForm == "wordcc"
    }
}

enum FakeFormName implements FormName {
    MAIN_FORM(true, 'a'),
    NOT_MANDATORY_FORM(false, 'b'),
    MANDATORY_AND_POSSIBLE_IRREGULAR(true, 'c'),
    NOT_MANDATORY_AND_IRREGULAR(false, 'd')

    final boolean isMandatory
    final String regularSuffix

    FakeFormName(boolean isMandatory, String regularSuffix) {
        this.isMandatory = isMandatory
        this.regularSuffix = regularSuffix
    }

    @Override
    boolean isMandatory() {
        return isMandatory
    }

    @Override
    Function<String, String> getStandardConverter() {
        return { it -> it + regularSuffix }
    }

    @Override
    FormName getRoot() {
        return null
    }

}