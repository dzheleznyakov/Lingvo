package com.zheleznyakov.lingvo.en

import com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech
import spock.lang.Specification
import spock.lang.Unroll

import static com.zheleznyakov.lingvo.en.EnNoun.EnNounFormName.NOMINATIVE_PLURAL
import static com.zheleznyakov.lingvo.en.EnNoun.EnNounFormName.NOMINATIVE_SINGLE
import static com.zheleznyakov.lingvo.en.EnNoun.EnNounFormName.POSSESSIVE_PLURAL
import static com.zheleznyakov.lingvo.en.EnNoun.EnNounFormName.POSSESSIVE_SINGLE

class EnNounSpec extends Specification {

    def "Test default properties of English Noun"() {
        given: "an English noun"
        def mainForm = "word"
        EnNoun noun = EnNoun.build(mainForm)

        expect: "its basic properties are correct"
        noun.language == EnglishLanguage.get()
        noun.partOfSpeech == PartOfSpeech.NOUN
        noun.mainForm == mainForm
        noun.regular
        !noun.properNoun
    }

    @Unroll
    def "The plural form of regular noun [#mainForm] is [#plural]"() {
        given: "an English noun"
        EnNoun enNoun = EnNoun.build(mainForm)

        expect: "the main form to be built correctly"
        with (enNoun) {
            getForm(NOMINATIVE_PLURAL) == plural
            regular
        }

        where: "the parameters are"
        mainForm   || plural
        "word"     || "words"
        "box"      || "boxes"
        "sandwich" || "sandwiches"
        "parish"   || "parishes"
        "miss"     || "misses"
        "toy"      || "toys"
        "thief"    || "thieves"
        "wife"     || "wives"
        "cliff"    || "cliffs"
    }

    def "The plural of irregular noun [man] is [men]"() {
        given: "an English noun 'man'"
        EnNoun man = EnNoun.builder("man")
                .withPlural("men")
                .build()

        expect:
        with(man) {
            getForm(NOMINATIVE_PLURAL) == "men"
            !regular
        }
    }

    @Unroll
    def "Get all forms of a regular English noun -- #mainForm"() {
        given: "an English noun"
        EnNoun enNoun = EnNoun.build(mainForm)

        expect: "the noun forms are computed correctly"
        enNoun.getForms() == [
                (NOMINATIVE_SINGLE) : mainForm,
                (NOMINATIVE_PLURAL) : plural,
                (POSSESSIVE_SINGLE) : possessiveSingle,
                (POSSESSIVE_PLURAL) : possesivePlural]

        where: "the parameters are"
        mainForm || plural   | possessiveSingle | possesivePlural
        "word"   || "words"  | "word's"         | "words'"
        "box"    || "boxes"  | "box'"           | "boxes'"
        "boss"   || "bosses" | "boss'"          | "bosses'"
    }

}
