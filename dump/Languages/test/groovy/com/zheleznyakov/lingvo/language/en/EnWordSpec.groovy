package com.zheleznyakov.lingvo.language.en

import com.zheleznyakov.lingvo.basic.Language
import com.zheleznyakov.lingvo.en.EnWord
import com.zheleznyakov.lingvo.en.word.*
import spock.lang.Specification
import spock.lang.Unroll

import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.ADJECTIVE
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.ADVERB
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.ARTICLE
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.CONJUNCTION
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.NOUN
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.PREPOSITION
import static com.zheleznyakov.lingvo.basic.words.parts.PartOfSpeech.VERB

class EnWordSpec extends Specification {

    @Unroll
    def "Test basic properties of simple English parts of speech -- #expectedPartOfSpeech -- #word"() {
        when: "an English word is created"
        EnWord enWord = builder(word)

        then: "all its basic properties are correct"
        with(enWord) {
            language == Language.ENGLISH
            partOfSpeech == expectedPartOfSpeech
            mainForm == word
            forms == [word]
            formsFull == expFormsFull
        }

        where: "words under test are"
        word    | builder                     || expectedPartOfSpeech                                         | expFormsFull
        "green" | { EnAdjective.build(it) }   || ADJECTIVE   | [word]
        "well"  | { EnAdverb.build(it) }      || ADVERB      | [word]
        "and"   | { EnConjunction.build(it) } || CONJUNCTION | [word]
        "of"    | { EnPreposition.build(it) } || PREPOSITION | [word]
        "a"     | { EnArticle.build(it) }     || ARTICLE     | ["a/an"]
        "the"   | { EnArticle.build(it) }     || ARTICLE     | ["the"]
    }

    @Unroll
    def "Test basic properties of complex English parts of speech -- #expectedPartOfSpeect -- #word"() {
        when: "an English word is created"
        EnWord enWord = builder(word)

        then: "all its basic properties are correct"
        with(enWord) {
            language == Language.ENGLISH
            partOfSpeech == expectedPartOfSpeect
            mainForm == word
            regular == true
        }

        where: "words under test are"
        word   | builder                            || expectedPartOfSpeect
        "toy"  | { EnNoun.build(it) }               || NOUN
        "John" | { EnNoun.builder(it).properNoun()} || NOUN
        "cut"  | { EnVerb.build(it) }               || VERB
    }

    @Unroll
    def "Test English word with transcription -- #word"() {
        when: "an English word with transcription is created"
        EnWord enWord = builder(word).
                transcription(transcription).
                build()

        then: "the transcription set on the adjective is correct"
        enWord.transcription == "[${transcription}]"

        where: "words and their transcriptions are"
        word     | transcription | builder
        "quick"  | "kwɪk"        | { EnAdjective.builder(it) }
        "easily" | "ˈiːzɪli"     | { EnAdverb.builder(it) }
        "or"     | "ɔː"          | { EnConjunction.builder(it) }
        "from"   | "frɒm"        | { EnPreposition.builder(it) }
    }

    def "Throw when creating a word with illegal symbols"() {
        when: "an creating a word with illegal symbol"
        EnNoun.build("abc" + illegalSymbol)

        then: "an Illegal Argument Exception is thrown"
        IllegalArgumentException exception = thrown()
        exception.getMessage().contains "illegal symbol"

        where: "the words are"
        illegalSymbol | _
        "ж"           | _
        "1"           | _
        "!"           | _
        "/"           | _
    }

    def "toString method includes main form of the word"() {
        given: "an English word"
        String mainForm = "word"
        EnWord word = EnNoun.build(mainForm)

        expect: "toString contains the main form"
        word.toString().contains mainForm
    }

}
