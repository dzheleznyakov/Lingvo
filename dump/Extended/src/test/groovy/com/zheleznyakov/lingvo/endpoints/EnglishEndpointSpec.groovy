package com.zheleznyakov.lingvo.endpoints

import spock.lang.Specification

import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.NOUN
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.VERB
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.ADVERB
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.ADJECTIVE
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.PREPOSITION
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.CONJUNCTION
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.ARTICLE

class EnglishEndpointSpec extends Specification {

    def "Get all English parts of speeches"() {
        expect: "an English endpoint returns all English parts of speeches"
        new EnglishEndpoint().getPartsOfSpeeches() == [NOUN, VERB, ADJECTIVE, ADVERB, PREPOSITION, CONJUNCTION, ARTICLE]
    }

}
