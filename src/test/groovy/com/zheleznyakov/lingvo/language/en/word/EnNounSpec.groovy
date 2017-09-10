package com.zheleznyakov.lingvo.language.en.word

import com.zheleznyakov.lingvo.language.en.word.EnNoun.EnNounFormName
import com.zheleznyakov.lingvo.language.en.word.EnProperNoun.EnProperNounFormName
import spock.lang.Specification
import spock.lang.Unroll

class EnNounSpec extends Specification {
    static final def EN_NOUN_BUILDER = { EnNoun.build(it)}
    static final def EN_PROPER_NOUN_BUILDER = { EnNoun.builder(it).properNoun()}

    @Unroll
    def "Test forms of regular English nouns with different endings -- #word"() {
        when: "a regular English noun is created"
        EnNoun noun = proper ? EN_PROPER_NOUN_BUILDER(word) : EN_NOUN_BUILDER(word)

        then: "all its basic properties are correct"
        with(noun) {
            properNoun == proper
            forms == expectedForms
            formsFull == expectedFormsFull
        }

        where: "the test parameters are"
        word       | proper || expectedForms                                           | expectedFormsDiff
        "house"    | false  || ["house", "houses", "house's", "houses'"]               | null
        "box"      | false  || ["box", "boxes", "box's", "boxes'"]                     | null
        "sandwich" | false  || ["sandwich", "sandwiches", "sandwich's", "sandwiches'"] | null
        "parish"   | false  || ["parish", "parishes", "parish's", "parishes'"]         | null
        "miss"     | false  || ["miss", "misses", "miss'", "misses'"]                  | null
        "city"     | false  || ["city", "cities", "city's", "cities'"]                 | null
        "boy"      | false  || ["boy", "boys", "boy's", "boys'"]                       | null
        "thief"    | false  || ["thief", "thieves", "thief's", "thieves'"]             | null
        "wife"     | false  || ["wife", "wives", "wife's", "wives'"]                   | null
        "cliff"    | false  || ["cliff", "cliffs", "cliff's", "cliffs'"]               | null
        "John"     | true   || ["John", "John's"]                                      | null
        "Thomas"   | true   || ["Thomas", "Thomas's"]                                  | ["Thomas", "Thomas's/Thomas'"]

        expectedFormsFull = expectedFormsDiff ?: expectedForms
    }

    @Unroll
    def "Test forms of irregular English nouns -- #nounSingle"() {
        when: "an irregular English noun is created"
        EnNoun noun = EnNoun.builder(nounSingle).irregularPlural(nounPlural)

        then: "its properties are correct"
        with(noun) {
            regular == false
            properNoun == false
            forms == expectedForms
            formsFull == expectedFormsFull
        }

        where: "the test parameters are"
        nounSingle | nounPlural || expectedForms                             | expectedFormsFull
        "man"      | "men"      || ["man", "men", "man's", "men's"]          | ["man", "men", "man's", "men's"]
        "hero"     | "heroes"     || ["hero", "heroes", "hero's", "heroes'"] | ["hero", "heroes", "hero's", "heroes'"]
    }

    def "Test full forms of an English word with alternative forms"() {
        when: "an English word with alternative form is crated"
        EnNoun noun = EnNoun.builder("realisation").alternativeForm("realization")

        then: "its full forms are correct"
        noun.formsFull == ["realisation/realization", "realisations/realizations", "realisation's/realization's", "realisations'/realizations'"]
    }

    @Unroll
    def "Test getting a single form of an English non-proper noun -- #formName"() {
        when: "an English noun is created"
        EnNoun noun = EnNoun.builder("man").irregularPlural("men")

        then: "its form is correct"
        noun.getForm(formName) == form

        where: "the test parameters are"
        formName                         || form
        EnNounFormName.NOMINATIVE_SINGLE || "man"
        EnNounFormName.NOMINATIVE_PLURAL || "men"
        EnNounFormName.POSSESSIVE_SINGLE || "man's"
        EnNounFormName.POSSESSIVE_PLURAL || "men's"
    }

    @Unroll
    def "Test getting a single form of an English proper noun -- #formName"() {
        when: "an English proper noun is created"
        EnNoun john = EnNoun.builder("John").properNoun()

        then: "its form is correct"
        john.getForm(formName) == form

        where: "the test parameters are"
        formName                                || form
        EnProperNounFormName.NOMINATIVE_SINGLE  || "John"
        EnProperNounFormName.POSSESSIVE_SINGLE  || "John's"
    }

}
