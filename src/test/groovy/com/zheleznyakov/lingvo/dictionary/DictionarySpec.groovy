package com.zheleznyakov.lingvo.dictionary

import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.PartOfSpeech
import com.zheleznyakov.lingvo.basic.Word
import com.zheleznyakov.lingvo.language.Language
import com.zheleznyakov.lingvo.language.en.word.EnNoun
import com.zheleznyakov.lingvo.language.en.word.EnVerb
import spock.lang.Specification

class DictionarySpec extends Specification {
    Dictionary dictionary
    static final String WORD_STING = "call"
    static final EnNoun NOUN_1 = EnNoun.build(WORD_STING)
    static final EnNoun NOUN_2 = EnNoun.build(WORD_STING)
    static final EnVerb VERB_1 = EnVerb.build(WORD_STING)
    static final String MEANING_1 = "звонок"
    static final String MEANING_2 = "крик"
    static final String MEANING_3 = "звонить"

    def setup() {
        dictionary = new Dictionary(Language.ENGLISH)
    }

    def "Create a dictionary and add a word in it"() {
        expect: "the dictionary does not contain the noun"
        !dictionary.contains(NOUN_1)
        !dictionary.getMeaning(NOUN_1)

        when: "a noun is added to the dictionary"
        dictionary.add(NOUN_1, MEANING_1)

        then: "the noun and its meaning are in the dictionary"
        dictionary.contains NOUN_1
        dictionary.getMeaning(NOUN_1) == MEANING_1
    }

    def "Throw when adding a word of another language"() {
        given: "a Spanish word"
        def esWord = Stub(Word)
        esWord.getLanguage() >> Language.SPANISH

        when: "the Spanish word is added to the English dictionary"
        dictionary.add(esWord, "hola")

        then: "an exception is thrown"
        thrown(IllegalArgumentException)
    }

    def "A dictionary may contain two words with the same main form and different meanings"() {
        when: "two words with their meanings are added to the dictionary"
        dictionary.add(NOUN_1, MEANING_1)
        dictionary.add(NOUN_2, MEANING_2)

        then: "both words and their meanings are in the dictionary"
        NOUN_1.mainForm == NOUN_2.mainForm
        dictionary.contains NOUN_1
        dictionary.contains NOUN_2
        dictionary.getMeaning(NOUN_1) == MEANING_1
        dictionary.getMeaning(NOUN_2) == MEANING_2
        dictionary.getWords(NOUN_1.mainForm) == ImmutableSet.of(NOUN_1, NOUN_2)
    }

    def "Test selecting words by part of speech"() {
        when: "words of different parts of speed are added to the dictionary"
        dictionary.add(NOUN_1, MEANING_1)
        dictionary.add(NOUN_2, MEANING_2)
        dictionary.add(VERB_1, MEANING_3)

        then: "nouns and verbs can be extracted separately"
        dictionary.getWords(PartOfSpeech.NOUN) == ImmutableSet.of(NOUN_1, NOUN_2)
        dictionary.getWords(PartOfSpeech.VERB) == ImmutableSet.of(VERB_1)
    }

    def "Test get all words from the dictionary"() {
        when: "words of different parts of speed are added to the dictionary"
        dictionary.add(NOUN_1, MEANING_1)
        dictionary.add(NOUN_2, MEANING_2)
        dictionary.add(VERB_1, MEANING_3)

        then: "all words can be extracted together"
        dictionary.getWords() == ImmutableSet.of(NOUN_1, NOUN_2, VERB_1)
    }

}
