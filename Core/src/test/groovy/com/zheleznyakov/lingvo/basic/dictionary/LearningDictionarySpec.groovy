package com.zheleznyakov.lingvo.basic.dictionary

import com.zheleznyakov.lingvo.basic.implementations.FakeEnglish
import com.zheleznyakov.lingvo.basic.implementations.TestableMultiFormWord
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord
import com.zheleznyakov.lingvo.basic.words.Language
import spock.lang.Specification

class LearningDictionarySpec extends Specification {
    private static final Language LANGUAGE = FakeEnglish.FIXED_LANGUAGE
    /*
    - A dictionary is bound to a particular language
    - Add a record to the dictionary
    - Learn in different modes (forward, backward, toggle)
    - Learn only those words, that are not completed
    - Check statistics (percentage)
    - Configure (set learned count)
    - See all records
    - Be able to update a record in the dictionary

    - Record
        = word : Word
        = meaning : String
        = examples : (String, String)[]
        = transcription : String

    - DictionaryConfig
        = maxCount : int
        = mode : Mode

    - Dictionary
        = records : Record[]
        = statistics : Record -> int
        = config : DictionaryConfig
     */

    def "Create a dictionary and add a simple record to it"() {
        given: "a word and its meaning"
        GrammaticalWord word = new TestableMultiFormWord("word")
        String meaning = "слово"

        and: "a dictionary"
        LearningDictionary dictionary = new LearningDictionary(LANGUAGE)

        expect: "the dictionary has not records"
        dictionary.getRecords().size() == 0

        when: "a (simple) record is added to the dictionary"
        dictionary.record(word, meaning).add()

        then: "the dictionary has one record"
        dictionary.getRecords().size() == 1

        and: "the record contains the given word and its meaning"
        with(dictionary.getRecords().get(0)) {
            it.word == word
            it.meaning == meaning
        }
    }

    def "Create a dictionary and add a full record to it"() {
        given: "a word, its meaning, transcription and usage example"
        GrammaticalWord word = new TestableMultiFormWord("word")
        String meaning = "слово"
        String transcription = "wəːd"
        Record.UsageExample example = ["To give a word", "Дать слово"]

        and: "a dictionary"
        LearningDictionary dictionary = new LearningDictionary(LANGUAGE)

        when: "a (full) record is added to the dictionary"
        dictionary.record(word, meaning)
                .withTranscription(transcription)
                .withUsageExamples([example])
                .add()

        then: "the record contains the given word, its meaning, transcription and usage example"
        with(dictionary.getRecords().get(0)) {
            it.word == word
            it.meaning == meaning
            it.transcription == transcription
            it.examples == [example]
        }
    }

    def "When adding a record with a word of a wrong language throw"() {
        given: "a language"
        Language anotherLanguage = new FakeEnglish()

        and: "a word and its meaning"
        def word = new TestableMultiFormWord("word")
        def meaning = "слово"

        and: "a dictionary for the language"
        LearningDictionary dictionary = new LearningDictionary(anotherLanguage)

        expect: "the languages of the word and dictionary are different"
        word.language != anotherLanguage

        when: "a record is added to the dictionary"
        dictionary.record(word, meaning).add()

        then: "an IllegalArgumentException is thrown"
        IllegalArgumentException exception = thrown()
    }
}
