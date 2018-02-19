package com.zheleznyakov.lingvo.basic.dictionary

import com.zheleznyakov.lingvo.basic.dictionary.Record.UsageExample
import com.zheleznyakov.lingvo.basic.implementations.FakeEnglish
import com.zheleznyakov.lingvo.basic.implementations.TestableMultiFormWord
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord
import com.zheleznyakov.lingvo.basic.words.Language
import spock.lang.Specification
import spock.lang.Unroll

class LearningDictionarySpec extends Specification {
    private static final Language LANGUAGE = FakeEnglish.FIXED_LANGUAGE
    private static final GrammaticalWord word = new TestableMultiFormWord("word")
    private static final String description = "слово"
    private static final String transcription = "wəːd"
    private static final Record.UsageExample example = ["To give a word", "Дать слово"]
    private static final UsageExample newExample = ["He gave me the word to start", "Он дал мне команду начинать"]

    def "Create a dictionary and add a simple record to it"() {
        given: "a dictionary"
        LearningDictionary dictionary = new LearningDictionary(LANGUAGE)

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

    def "When updating a usage example, it keeps its position in the example list"() {
        given: "a dictionary with one record with three usage examples"
        UsageExample example1 = ["1", "1"]
        UsageExample example2 = ["2", "2"]
        UsageExample example3 = ["3", "3"]
        LearningDictionary dictionary = new LearningDictionary(LANGUAGE)
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
        LearningDictionary dictionary = new LearningDictionary(LANGUAGE)
        addFullRecordToDictionary(dictionary)

        when: "the record is removed from the dictionary"
        dictionary.remove(getRecord(dictionary))

        then: "the dictionary contains no records"
        dictionary.records.isEmpty()
    }

    def "A newly created dictionary has default config"() {
        given: "a newly created learning dictionary"
        LearningDictionary dictionary = [LANGUAGE]

        expect: "the dictionary config to be default"
        dictionary.config == LearningDictionaryConfig.default
    }

    private def addFullRecordToDictionary(LearningDictionary dictionary) {
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
