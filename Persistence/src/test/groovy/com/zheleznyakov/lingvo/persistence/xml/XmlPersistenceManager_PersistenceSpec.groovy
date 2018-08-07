package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableMap
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig
import com.zheleznyakov.lingvo.basic.dictionary.Record
import com.zheleznyakov.lingvo.helpers.DictionaryRecordTestHelper
import com.zheleznyakov.lingvo.implementations.FakeEnglish
import com.zheleznyakov.lingvo.persistence.PersistenceHelper
import com.zheleznyakov.lingvo.persistence.xml.util.IOTestHelper
import spock.lang.Specification

import java.util.function.Function

class XmlPersistenceManager_PersistenceSpec extends Specification {
    private static final String DICTIONARY_NAME = 'Test'

    private XmlPersistenceManager persistenceManager

    private LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE, DICTIONARY_NAME]

    void setup() {
        PersistenceHelper.loadWordSerializers('com.zheleznyakov.lingvo.persistence.xml.util')
        persistenceManager = []
    }

    def cleanup() {
        IOTestHelper.clean()
    }

    def "If xml file with dictionary does not exists, persistence manager creates a new one"() {
        expect: "that the xml file with the dictionary does not exist"
        File file = getDictionaryFileToRead()
        !file.exists()

        when: "the dictionary is persisted"
        persistenceManager.persist dictionary

        then: "the xml file with the dictionary exists"
        file.exists()
    }

    def "if xml file exists, then it gets overridden"() {
        given: 'the xml file with unrelated data'
        File file = getDictionaryFileToWrite()
        file.delete()
        String initialFileContent = '<root>some data</root>'
        file << initialFileContent

        when: 'the dictionary is persisted'
        persistenceManager.persist dictionary

        then: 'the file\'s content is overridden'
        assertPersistenceMetadata()
        assertPersistedDictionaryConfig(file)
        assertPersistedRecords(file)
        !file.text.contains(initialFileContent);
    }

    def "Files for dictionary of the same language are stored in the same folder"() {
        given: "two dictionaries of the same language"
        LearningDictionary secondDictionary = [dictionary.language, "Second"]

        expect: "that neither dictionary is persisted"
        File file1 = getDictionaryFileToRead()
        File file2 = getDictionaryFileToRead(secondDictionary)
        !file1.exists()
        !file2.exists()

        when: "the dictionaries are persisted"
        persistenceManager.persist dictionary
        persistenceManager.persist secondDictionary

        then: "files for both dictionaries are in the same folder"
        file1.exists()
        file2.exists()
        file1.parent == file2.parent
    }

    def "When a dictionary is persisted, them xml file contains persistence metadata"() {
        when: "the dictionary is persisted"
        persistenceManager.persist dictionary

        then: "the xml file contains persistence metadata"
        assertPersistenceMetadata()
    }

    def "When a dictionary is persisted, its name and language are persisted as well"() {
        when: "the dictionary is persisted"
        persistenceManager.persist dictionary

        then: "its language is persisted"
        def root = new XmlSlurper().parse dictionaryFileToWrite
        root.dictionary.language == dictionary.language.code()

        and: "its name is persisted"
        root.dictionary.name == dictionary.name
    }

    def "When a dictionary is persisted, its config is persisted as well"() {
        given: "the dictionaries setting in the config are not default"
        dictionary.config.maxLearnCount = 10
        dictionary.config.strict = true
        dictionary.config.mode = LearningDictionaryConfig.Mode.TOGGLE

        when: "the dictionary is persisted"
        persistenceManager.persist dictionary

        then: "the dictionaries config is persisted as well"
        assertPersistedDictionaryConfig(dictionaryFileToWrite)
    }

    def "When an empty dictionary is persisted, it has no records"() {
        expect: "the dictionary to be empty"
        dictionary.records.isEmpty()

        when: "the dictionary is persisted"
        persistenceManager.persist dictionary

        then: "no records are persisted"
        assertPersistedRecords(dictionaryFileToWrite)
    }

    def "When a non-empty dictionary is persisted, all records are persisted"() {
        given: "the dictionary has 10 records"
        DictionaryRecordTestHelper.addFullRecordsToDictionary(dictionary, 10)

        when: "the dictionary is persisted"
        persistenceManager.persist dictionary

        then: "then all records are persisted"
        assertPersistedRecords(dictionaryFileToWrite, 10)
    }

    private File getDictionaryFileToRead(dictionary = this.dictionary) {
        IOTestHelper.getDictionaryFileToRead(dictionary.name)
    }

    private File getDictionaryFileToWrite(dictionary = this.dictionary) {
        IOTestHelper.getDictionaryFileToWrite(dictionary.name)
    }

    private void assertPersistenceMetadata() {
        def root = new XmlSlurper().parse(dictionaryFileToWrite)
        root.persistenceManager.@version == "v1"
        root.persistenceManager.@type == "xml"

        true
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
            def xmlRecord = entry.key
            def mainForm = xmlRecord.word.mainForm.toString()
            def expectedRecord = recordsByMainForm[mainForm]
            assert xmlRecord.description == expectedRecord.description
            assert xmlRecord.transcription == expectedRecord.transcription
            assert xmlRecord.examples.elem.size() == expectedRecord.examples.size()
            assert xmlRecord.examples.elem.@type == Record.UsageExample.typeName
            assert xmlRecord.examples.elem[0].example == expectedRecord.examples[0].example
            assert xmlRecord.examples.elem[0].translation == expectedRecord.examples[0].translation

            def xmlWord = xmlRecord.word
            assert xmlWord.@class == expectedRecord.word.class.canonicalName
            assert xmlWord.mainForm == expectedRecord.word.mainForm
            assert xmlWord.randomValue == expectedRecord.word.randomValue
        }

        true
    }
}
