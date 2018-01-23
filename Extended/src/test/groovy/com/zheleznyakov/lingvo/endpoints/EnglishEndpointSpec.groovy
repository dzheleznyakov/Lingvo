package com.zheleznyakov.lingvo.endpoints

import spock.lang.Specification

import static com.zheleznyakov.lingvo.basic.parts.PartOfSpeech.NOUN
import static com.zheleznyakov.lingvo.basic.parts.PartOfSpeech.VERB
import static com.zheleznyakov.lingvo.basic.parts.PartOfSpeech.ADVERB
import static com.zheleznyakov.lingvo.basic.parts.PartOfSpeech.ADJECTIVE
import static com.zheleznyakov.lingvo.basic.parts.PartOfSpeech.PREPOSITION
import static com.zheleznyakov.lingvo.basic.parts.PartOfSpeech.CONJUNCTION
import static com.zheleznyakov.lingvo.basic.parts.PartOfSpeech.ARTICLE

class EnglishEndpointSpec extends Specification {

    def "Get all English parts of speeches"() {
        expect: "an English endpoint returns all English parts of speeches"
        new EnglishEndpoint().getPartsOfSpeeches() == [NOUN, VERB, ADJECTIVE, ADVERB, PREPOSITION, CONJUNCTION, ARTICLE]
    }

}
