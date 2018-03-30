package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.helpers.DictionaryRecordTestHelper
import com.zheleznyakov.lingvo.implementations.FakeEnglish
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import spock.lang.Specification

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

    private File getDictionaryFile() {
        new File("${PATH_TO_FILE_STORAGE}/${FakeEnglish.FIXED_LANGUAGE.code()}/xml/${dictionary.name}.xml")
    }

    def "When persisting an empty dictionary, it has not records"() {
        expect: "the dictionary to be empty"
        dictionary.records.isEmpty()

        when: "the dictionary is persisted"
        persistenceManager.persist(dictionary)

        then: "no records are persisted"
        assertPersistedRecords(dictionaryFile)
    }

    def "When a non-empty dictionary, all records are persisted"() {
        given: "the dictionary has 10 records"
        DictionaryRecordTestHelper.addFullRecordsToDictionary(dictionary, 10)

        when: "the dictionary is persisted"
        persistenceManager.persist(dictionary)

        then: "then all records are persisted"
        assertPersistedRecords(dictionaryFile, 10)

    }

    private static assertPersistedRecords(File file, int expectedNumberOfRecords = 0) {
        def dictionary = new XmlSlurper().parse(file).dictionary

        assert dictionary.name == DICTIONARY_NAME
        assert dictionary.language == FakeEnglish.FIXED_LANGUAGE.toString()
        assert dictionary.records.record.size() == expectedNumberOfRecords

        true
    }
}
