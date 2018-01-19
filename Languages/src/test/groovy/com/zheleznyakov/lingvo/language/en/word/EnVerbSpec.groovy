package com.zheleznyakov.lingvo.language.en.word

import com.zheleznyakov.lingvo.en.word.EnVerb
import spock.lang.Specification
import spock.lang.Unroll

import static com.zheleznyakov.lingvo.en.word.EnVerb.EnVerbFormName.GERUND
import static com.zheleznyakov.lingvo.en.word.EnVerb.EnVerbFormName.MAIN_FORM
import static com.zheleznyakov.lingvo.en.word.EnVerb.EnVerbFormName.PAST_PARTICIPLE
import static com.zheleznyakov.lingvo.en.word.EnVerb.EnVerbFormName.PAST_PLURAL
import static com.zheleznyakov.lingvo.en.word.EnVerb.EnVerbFormName.PAST_SINGLE
import static com.zheleznyakov.lingvo.en.word.EnVerb.EnVerbFormName.PRESENT_FIRST_SINGULAR
import static com.zheleznyakov.lingvo.en.word.EnVerb.EnVerbFormName.PRESENT_PLURAL
import static com.zheleznyakov.lingvo.en.word.EnVerb.EnVerbFormName.PRESENT_THIRD_SINGULAR


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
        word     | phrasePart | root | builder                || expectedForms
        "test"   | null       | null | EN_VERB_BUILDER        || ["test", "tests", "testing", "tested"]
        "miss"   | null       | null | EN_VERB_BUILDER        || ["miss", "misses", "missing", "missed"]
        "wish"   | null       | null | EN_VERB_BUILDER        || ["wish", "wishes", "wishing", "wished"]
        "match"  | null       | null | EN_VERB_BUILDER        || ["match", "matches", "matching", "matched"]
        "fix"    | null       | null | EN_VERB_BUILDER        || ["fix", "fixes", "fixing", "fixed"]
        "move"   | null       | null | EN_VERB_BUILDER        || ["move", "moves", "moving", "moved"]
        "apply"  | null       | null | EN_VERB_BUILDER        || ["apply", "applies", "applying", "applied"]
        "employ" | null       | null | EN_VERB_BUILDER        || ["employ", "employs", "employing", "employed"]
        "log"    | null       | null | EN_VERB_BUILDER        || ["log", "logs", "logging", "logged"]
        "refer"  | null       | null | EN_VERB_BUILDER        || ["refer", "refers", "referring", "referred"]
        "pull"   | "off"      | null | EN_VERB_PHRASE_BUILDER || ["pull off", "pulls off", "pulling off", "pulled off"]
    }

    def "Test forms of irregular English verbs -- to be"() {
        when: "English verb 'to be' is created"
        EnVerb be = EnVerb.builder("be").
                withIrregularForm(GERUND, "being").
                withIrregularForm(PRESENT_FIRST_SINGULAR, "am").
                withIrregularForm(PRESENT_THIRD_SINGULAR, "is").
                withIrregularForm(PRESENT_PLURAL, "are").
                withIrregularForm(PAST_SINGLE, "was").
                withIrregularForm(PAST_PLURAL, "were").
                withIrregularForm(PAST_PARTICIPLE, "been").
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
                withIrregularForm(PRESENT_THIRD_SINGULAR, "has").
                withIrregularForm(PAST_SINGLE, "had").
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
