package com.zheleznyakov.lingvo.basic.words.parts

import spock.lang.Specification

import java.util.stream.Collectors

class PartOfSpeechSpec extends Specification {
    def "Check that all required parts of speeches are present"() {
        given: "actual encoded parts of speeches"
        Set<String> actualPartsOfSpeechesBrief = Arrays.stream(PartOfSpeech.values())
                .map { partOfSpeech -> partOfSpeech.brief }
                .collect(Collectors.toSet())

        and: "required parts of speeches"
        Set<String> expectedPartsOfSpeechesBrief = ["n", "v", "adj", "adv", "prep", "conj", "art"].toSet()

        expect: "that actual and expected parts of speeches are the same"
        actualPartsOfSpeechesBrief == expectedPartsOfSpeechesBrief
    }
}
