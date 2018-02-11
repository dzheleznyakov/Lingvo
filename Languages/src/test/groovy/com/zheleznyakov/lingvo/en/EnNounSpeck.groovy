package com.zheleznyakov.lingvo.en

import com.zheleznyakov.lingvo.basic.parts.PartOfSpeech
import spock.lang.Specification

class EnNounSpeck extends Specification {

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

//    @Unroll
//    def "Get plural forms for regular English noun #noun"() {
//        given: "an English noun"
//        EnNoun noun = EnNoun.build(noun)
//
////        where: "the parameters are"
////        noun   |
////        "word" |
//    }

}
