package com.zheleznyakov.lingvo.basic.dictionary

import com.zheleznyakov.lingvo.basic.dictionary.Record.UsageExample
import com.zheleznyakov.lingvo.basic.implementations.FakeEnglish
import com.zheleznyakov.lingvo.basic.implementations.TestableMultiFormWord
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord
import com.zheleznyakov.lingvo.basic.words.Language
import com.zheleznyakov.lingvo.helpers.LearningDictionaryConfigHelper
import spock.lang.Specification
import spock.lang.Unroll

class LearningDictionarySpec extends Specification {
    private static final Language LANGUAGE = FakeEnglish.FIXED_LANGUAGE
    private static final GrammaticalWord word = new TestableMultiFormWord("word")
    private static final String description = "слово"
    private static final String transcription = "wəːd"
    private static final Record.UsageExample example = ["To give a word", "Дать слово"]
    private static final UsageExample newExample = ["He gave me the word to start", "Он дал мне команду начинать"]

    private LearningDictionary dictionary = new LearningDictionary(LANGUAGE)

    def "Create a dictionary and add a simple record to it"() {
        expect: "the dictionary has not records"
        dictionary.getRecords().size() == 0

        and: "the dictionary language to be correct"
        dictionary.language == LANGUAGE

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
        when: "a (full) record is added to the dictionary"
        addFullRecordToDictionary()

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
        dictionary = new LearningDictionary(dictionaryLanguage)

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
        addFullRecordToDictionary()

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
        addFullRecordToDictionary()

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
        addFullRecordToDictionary()

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

    def "When updating a usage example, it keeps its position in the example list"() {
        given: "a dictionary with one record with three usage examples"
        UsageExample example1 = ["1", "1"]
        UsageExample example2 = ["2", "2"]
        UsageExample example3 = ["3", "3"]
        dictionary.record(word, description)
                .withUsageExamples([example1, example2, example3])
                .add()

        when: "the second example is updated"
        UsageExample updatedExample2 = ["2'", "2'"]
        dictionary.updateExample(getRecord(dictionary), example2, updatedExample2)

        then: "the updated example is on the second position of the example list in the record"
        getRecord(dictionary).examples == [example1, updatedExample2, example3]
    }

    def "Remove a record from the dictionary"() {
        given: "a dictionary with one record"
        addFullRecordToDictionary()

        when: "the record is removed from the dictionary"
        dictionary.remove(getRecord(dictionary))

        then: "the dictionary contains no records"
        dictionary.records.isEmpty()
    }

    def "A newly created dictionary has default config"() {
        expect: "the config of a newly created dictionary is to be default"
        LearningDictionaryConfigHelper.areConfigsEqual(dictionary.config, LearningDictionaryConfig.default)
    }

    def "Test Record.toString method"() {
        given: "the record parameters"
        def mainForm = "word"
        def description = "description"
        def transcription = "transcription"
        def usageExample1 = ["example 1", "translation 1"] as UsageExample
        def usageExample2 = ["example 2", "translation 2"] as UsageExample

        and: "a record"
        dictionary.record([mainForm] as TestableMultiFormWord, description)
                .withTranscription(transcription)
                .withUsageExamples([usageExample1,usageExample2])
                .add()
        Record record = dictionary.records.iterator().next()

        expect: "the String representation of the record to be as expected"
        record.toString() ==
                "Record{word=${record.word.toString()}, description=${description}, transcription=${transcription}, " +
                "examples=[UsageExample{example=${usageExample1.example}, translation=${usageExample1.translation}}, " +
                          "UsageExample{example=${usageExample2.example}, translation=${usageExample2.translation}}]" +
                "}"
    }

    private def addFullRecordToDictionary() {
        dictionary.record(word, description)
                .withTranscription(transcription)
                .withUsageExample(example)
                .add()
        assert dictionary.records.size() == 1
    }

    private def getRecord(dictionary) {
        dictionary.records.iterator().next()
    }
}
