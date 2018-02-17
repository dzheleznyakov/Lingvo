package com.zheleznyakov.lingvo.basic.dictionary

import com.zheleznyakov.lingvo.basic.dictionary.Record.UsageExample
import com.zheleznyakov.lingvo.basic.implementations.FakeEnglish
import com.zheleznyakov.lingvo.basic.implementations.TestableMultiFormWord
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord
import com.zheleznyakov.lingvo.basic.words.Language
import spock.lang.Specification
import spock.lang.Unroll

class LearningDictionary_RecordsSpec extends Specification {
    private static final Language LANGUAGE = FakeEnglish.FIXED_LANGUAGE
    private static final GrammaticalWord word = new TestableMultiFormWord("word")
    private static final String description = "слово"
    private static final String transcription = "wəːd"
    private static final Record.UsageExample example = ["To give a word", "Дать слово"]
    private static final UsageExample newExample = ["He gave me the word to start", "Он дал мне команду начинать"]

    /*
    (-) A dictionary is bound to a particular language
    (-) Add a record to the dictionary
    - Be able to update a record in the dictionary
    - Learn in different modes (forward, backward, toggle)
    - Learn only those words, that are not completed
    - Check statistics (percentage)
    - Configure (set learned count)
    (-) See all records

    - Record
        = word : Word
        = description : String
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
        given: "a dictionary"
        LearningDictionary dictionary = new LearningDictionary(LANGUAGE)

        expect: "the dictionary has not records"
        dictionary.getRecords().size() == 0

        when: "a (simple) record is added to the dictionary"
        dictionary.record(word, description).add()

        then: "the dictionary has one record"
        dictionary.getRecords().size() == 1

        and: "the record contains the given word and its description"
        with(getRecord(dictionary)) {
            it.word == word
            it.description == description
        }
    }

    def "Create a dictionary and add a full record to it"() {
        given: "a dictionary"
        LearningDictionary dictionary = new LearningDictionary(LANGUAGE)

        when: "a (full) record is added to the dictionary"
        addFullRecordToDictionary(dictionary)

        then: "the record contains the given word, its description, transcription and usage example"
        with(getRecord(dictionary)) {
            it.word == word
            it.description == description
            it.transcription == transcription
            it.examples == [example]
        }
    }

    def "When adding a record with a word of a wrong language throw"() {
        given: "a new language"
        def dictionaryLanguage = new FakeEnglish("New Fake English", "NFE")

        and: "a dictionary"
        LearningDictionary dictionary = new LearningDictionary(dictionaryLanguage)

        expect: "the languages of the word and dictionary are different"
        word.language != dictionaryLanguage

        when: "a record is added to the dictionary"
        dictionary.record(word, description).add()

        then: "an IllegalArgumentException is thrown"
        IllegalArgumentException exception = thrown()
        exception.message == "Illegal language of a word: required [${dictionaryLanguage}], found [${LANGUAGE}]"
    }

    def "Test updating a record description"() {
        given: "a dictionary with the record"
        LearningDictionary dictionary = new LearningDictionary(LANGUAGE)
        addFullRecordToDictionary(dictionary)

        and: "a new description"
        String newDescription = "слово; комманда, сигнал"

        when: "the record description is updated"
        dictionary.updateDescription(getRecord(dictionary), newDescription)

        then: "the dictionary still contains one word"
        dictionary.records.size() == 1

        and: "the record contains new description and the old word, transcription and usage examples"
        with(getRecord(dictionary)) {
            it.word == word
            it.examples == [example]
            it.transcription == transcription
            it.description == newDescription
        }
    }

    def "Test updating a record transcription"() {
        given: "a dictionary with the record"
        LearningDictionary dictionary = new LearningDictionary(LANGUAGE)
        addFullRecordToDictionary(dictionary)

        and: "a new transcription"
        String newTranscription = "ворд"

        when: "the record transcription is updated"
        dictionary.updateTranscription(getRecord(dictionary), newTranscription)

        then: "the dictionary still contains one word"
        dictionary.records.size() == 1

        and: "the record contains new transcription and the old word, transcription and usage examples"
        with(getRecord(dictionary)) {
            it.word == word
            it.description == description
            it.examples == [example]
            it.transcription == newTranscription
        }
    }

    @Unroll
    def "Test updating a record usage examples -- #test"() {
        given: "a dictionary with the record"
        LearningDictionary dictionary = new LearningDictionary(LANGUAGE)
        addFullRecordToDictionary(dictionary)

        when: "the record examples are updated"
        updateFunction(dictionary, getRecord(dictionary), exampleInQuestion)

        then: "the dictionary still contains one word"
        dictionary.records.size() == 1

        and: "the record is the same apart from the updated usage examples"
        with(getRecord(dictionary)) {
            it.word == word
            it.description == description
            it.transcription == transcription
            it.examples == expectedExamples
        }

        where: "the parameters are"
        test     | exampleInQuestion | updateFunction                                             || expectedExamples
        "add"    | newExample        | { dic, rec, ex -> dic.addExample(rec, ex) }                || [example, newExample]
        "remove" | example           | { dic, rec, ex -> dic.removeExample(rec, ex) }             || []
        "update" | example           | { dic, rec, ex -> dic.updateExample(rec, ex, newExample) } || [newExample]
    }

    private def addFullRecordToDictionary(LearningDictionary dictionary) {
        dictionary.record(word, description)
                .withTranscription(transcription)
                .withUsageExample(example)
                .add()
    }

    private def getRecord(dictionary) {
        dictionary.records.iterator().next()
    }
}
