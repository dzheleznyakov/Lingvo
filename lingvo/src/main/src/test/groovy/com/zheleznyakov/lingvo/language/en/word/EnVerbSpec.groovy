package com.zheleznyakov.lingvo.language.en.word

import spock.lang.Specification
import spock.lang.Unroll

import static com.zheleznyakov.lingvo.language.en.word.EnVerb.EnVerbFormName.*

class EnVerbSpec extends Specification {
    static final def EN_VERB_BUILDER = { EnVerb.builder(it) }
    static final def EN_VERB_PHRASE_BUILDER = {verb, phrase -> EnVerb.builder(verb).withPhrasePart(phrase) }

    @Unroll
    def "Test forms of regular English verbs with different endings -- to #word"() {
        when: "a regular English verb is created"
        EnVerb verb = (phrasePart ? EN_VERB_PHRASE_BUILDER(word, phrasePart) : EN_VERB_BUILDER(word)).
                build()

        then: "all its basic properties are correct"
        with(verb) {
            forms == expectedForms
            formsFull == expectedForms
        }

        where: "the test parameters are"
        word     | phrasePart || expectedForms
        "test"   | null       || ["test", "tests", "testing", "tested"]
        "miss"   | null       || ["miss", "misses", "missing", "missed"]
        "wish"   | null       || ["wish", "wishes", "wishing", "wished"]
        "match"  | null       || ["match", "matches", "matching", "matched"]
        "fix"    | null       || ["fix", "fixes", "fixing", "fixed"]
        "move"   | null       || ["move", "moves", "moving", "moved"]
        "apply"  | null       || ["apply", "applies", "applying", "applied"]
        "employ" | null       || ["employ", "employs", "employing", "employed"]
        "log"    | null       || ["log", "logs", "logging", "logged"]
        "refer"  | null       || ["refer", "refers", "referring", "referred"]
        "pull"   | "off"      || ["pull off", "pulls off", "pulling off", "pulled off"]
    }

    def "Test forms of irregular English verbs -- to be"() {
        when: "English verb 'to be' is created"
        EnVerb be = EnVerb.builder("be").
                irregularForm(GERUND, "being").
                irregularForm(PRESENT_FIRST_SINGULAR, "am").
                irregularForm(PRESENT_THIRD_SINGULAR, "is").
                irregularForm(PRESENT_PLURAL, "are").
                irregularForm(PAST_SINGLE, "was").
                irregularForm(PAST_PLURAL, "were").
                irregularForm(PAST_PARTICIPLE, "been").
                build()

        then:
        be.forms == ["be", "am", "is", "are", "being", "was", "were", "been"]
        be.formsFull == be.forms
    }

    @Unroll
    def "Test full forms of English verbs with alternative forms -- to #word (#phrasePart)"() {
        when: "an English verb with alternative forms is created"
        EnVerb verb = (phrasePart ? EN_VERB_PHRASE_BUILDER(word, phrasePart) : EN_VERB_BUILDER(word)).
                alternativeForm(alternativeForm)

        then: "its full forms are correct"
        verb.getFormsFull() == expectedFullForms

        where: "the test parameters are"
        word       | phrasePart | alternativeForm || expectedFullForms
        "realise"  | null       | "realize"       || ["realise/realize", "realises/realizes", "realising/realizing", "realised/realized"]
        "realise"  | "out"      | "realize"       || ["realise out/realize out", "realises out/realizes out", "realising out/realizing out", "realised out/realized out"]
        "test"     | null       | null            || ["test", "tests", "testing", "tested"]
    }

    @Unroll
    def "Get one form of English verbs at a time -- #formName"() {
        when: "an English verb is created"
        EnVerb have = EnVerb.builder("have").
                irregularForm(PRESENT_THIRD_SINGULAR, "has").
                irregularForm(PAST_SINGLE, "had").
                build()

        then: "its form is correct"
        have.getForm(formName) == form

        where: "the forms are"
        formName               | form
        MAIN_FORM              | "have"
        PRESENT_FIRST_SINGULAR | "have"
        PRESENT_THIRD_SINGULAR | "has"
        PRESENT_PLURAL         | "have"
        GERUND                 | "having"
        PAST_SINGLE            | "had"
        PAST_PLURAL            | "had"
        PAST_PARTICIPLE        | "had"
    }

    def "Throw when creating a verb phrase with illegal symbol in phrase part"() {
        when: "an English verb phrase with illegal symbol in phrase part"
        EnVerb.builder("abc").
                withPhrasePart("a!").
                build()

        then: "an Illegal Argument Exception is thrown"
        IllegalArgumentException exception = thrown()
        exception.getMessage().contains "illegal symbol"
    }

}
