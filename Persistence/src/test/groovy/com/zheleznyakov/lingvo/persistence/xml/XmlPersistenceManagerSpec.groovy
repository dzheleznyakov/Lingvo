package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableMap
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig
import com.zheleznyakov.lingvo.helpers.DictionaryRecordTestHelper
import com.zheleznyakov.lingvo.implementations.FakeEnglish
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import spock.lang.Specification

import java.util.function.Function

class XmlPersistenceManagerSpec extends Specification {
    private static final String DICTIONARY_NAME = "Test"
    private static final String PATH_TO_FILE_STORAGE = ZhConfigFactory.get().getString("persistence.xml.root")

    private XmlPersistenceManager persistenceManager = []
    private LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE, this.DICTIONARY_NAME]

    def cleanup() {
        IOTestHelper.makeFolderEmpty(PATH_TO_FILE_STORAGE)
    }

    def "If xml file with dictionary does not exists, persistence manager creates a new one"() {
        expect: "that the xml file with the dictionary does not exist"
        File file = getDictionaryFile()
        !file.exists()

        when: "the dictionary is persisted"
        persistenceManager.persist(dictionary)

        then: "the xml file with the dictionary exists"
        file.exists()

        and: "the persistence manager metadata is persisted"
        def root = new XmlSlurper().parse(file)
        root.persistenceManager.@version == "v1"
        root.persistenceManager.@type == "xml"
    }

    def "Files for dictionary of the same language are stored in the same folder"() {
        given: "two dictionaries of the same language"
        LearningDictionary secondDictionary = [dictionary.language, "Second"]

        expect: "that neither dictionary is persisted"
        File file1 = getDictionaryFile()
        File file2 = getDictionaryFile(secondDictionary)
        !file1.exists()
        !file2.exists()

        when: "the dictionaries are persisted"
        persistenceManager.persist(dictionary)
        persistenceManager.persist(secondDictionary)

        then: "files for both dictionaries are in the same folder"
        file1.exists()
        file2.exists()
        file1.parent == file2.parent
    }

    def "When an empty dictionary is persisted, it has not records"() {
        expect: "the dictionary to be empty"
        dictionary.records.isEmpty()

        when: "the dictionary is persisted"
        persistenceManager.persist(dictionary)

        then: "no records are persisted"
        assertPersistedRecords(dictionaryFile)
    }

    def "When a non-empty dictionary is persisted, all records are persisted"() {
        given: "the dictionary has 10 records"
        DictionaryRecordTestHelper.addFullRecordsToDictionary(dictionary, 10)

        when: "the dictionary is persisted"
        persistenceManager.persist(dictionary)

        then: "then all records are persisted"
        assertPersistedRecords(dictionaryFile, 10)
    }

    def "The dictionary config is persisted with the dictionary"() {
        given: "the dictionaries setting in the config are not default"
        dictionary.config.maxLearnCount = 10
        dictionary.config.strict = true
        dictionary.config.mode = LearningDictionaryConfig.Mode.TOGGLE

        when: "the dictionary is persisted"
        persistenceManager.persist(dictionary)

        then: "the dictionaries config is persisted as well"
        assertPersistedDictionaryConfig(dictionaryFile)
    }

    private File getDictionaryFile(dictionary=this.dictionary) {
        new File("${PATH_TO_FILE_STORAGE}/${FakeEnglish.FIXED_LANGUAGE.code()}/xml/${dictionary.name}.xml")
    }

    private assertPersistedDictionaryConfig(File file) {
        def config = new XmlSlurper().parse(file).dictionary.config
        assert config.size() == 1
        assert config.maxLearnCount == dictionary.config.maxLearnCount
        assert config.strict == dictionary.config.strict
        assert config.mode == dictionary.config.mode.toString()

        true
    }

    private assertPersistedRecords(File file, int expectedNumberOfRecords = 0) {
        def recordsByMainForm = dictionary.records.stream()
                .collect(ImmutableMap.toImmutableMap(
                        { record -> record.word.mainForm as String},
                        Function.identity()
        ))

        def dictionary = new XmlSlurper().parse(file).dictionary

        assert dictionary.name == DICTIONARY_NAME
        assert dictionary.language == FakeEnglish.FIXED_LANGUAGE.code()

        def records = dictionary.records.entry
        assert records.size() == expectedNumberOfRecords
        records.each { entry ->
            def xmlRecord = entry.Record
            def mainForm = xmlRecord.word.mainForm.toString()
            def expectedRecord = recordsByMainForm[mainForm]
            assert xmlRecord.description == expectedRecord.description
            assert xmlRecord.transcription == expectedRecord.transcription
            assert xmlRecord.examples.UsageExample.size() == expectedRecord.examples.size()
            assert xmlRecord.examples.UsageExample[0].example == expectedRecord.examples[0].example
            assert xmlRecord.examples.UsageExample[0].translation == expectedRecord.examples[0].translation

            def xmlWord = xmlRecord.word
            assert xmlWord.@class == expectedRecord.word.class.simpleName
            assert xmlWord.mainForm == expectedRecord.word.mainForm
            assert xmlWord.randomValue == expectedRecord.word.randomValue
        }

        true
    }
}
