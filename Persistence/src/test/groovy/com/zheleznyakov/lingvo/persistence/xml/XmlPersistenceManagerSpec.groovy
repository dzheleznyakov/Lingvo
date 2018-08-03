package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableMap
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig
import com.zheleznyakov.lingvo.basic.dictionary.Record
import com.zheleznyakov.lingvo.helpers.DictionaryRecordTestHelper
import com.zheleznyakov.lingvo.implementations.FakeEnglish
import com.zheleznyakov.lingvo.persistence.PersistenceHelper
import com.zheleznyakov.lingvo.persistence.xml.util.IOTestHelper
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import org.junit.Ignore
import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Function

class XmlPersistenceManagerSpec extends Specification {
    private static final String DICTIONARY_NAME = 'Test'
    private static final String PATH_TO_FILE_STORAGE = ZhConfigFactory.get().getString('persistence.xml.root')

    private XmlPersistenceManager persistenceManager

    private LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE, DICTIONARY_NAME]

    void setup() {
        PersistenceHelper.loadWordSerializers('com.zheleznyakov.lingvo.persistence.xml.util')
        persistenceManager = []
    }

    def cleanup() {
        IOTestHelper.makeFolderEmpty(PATH_TO_FILE_STORAGE)
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
        def root = new XmlSlurper().parse(dictionaryFileToWrite)
        root.persistenceManager.@version == "v1"
        root.persistenceManager.@type == "xml"
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

    def "The dictionary config is persisted with the dictionary"() {
        given: "the dictionaries setting in the config are not default"
        dictionary.config.maxLearnCount = 10
        dictionary.config.strict = true
        dictionary.config.mode = LearningDictionaryConfig.Mode.TOGGLE

        when: "the dictionary is persisted"
        persistenceManager.persist dictionary

        then: "the dictionaries config is persisted as well"
        assertPersistedDictionaryConfig(dictionaryFileToWrite)
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

    private File getDictionaryFileToRead(dictionary=this.dictionary) {
        new File("${PATH_TO_FILE_STORAGE}/${FakeEnglish.FIXED_LANGUAGE.code()}/xml/${dictionary.name}.xml")
    }

    private File getDictionaryFileToWrite() {
        File file = dictionaryFileToRead
        if (!file.parentFile.exists())
            file.parentFile.mkdirs()
        return file
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

    def "If xml file does not exist, throw"() {
        given: "the xml file"
        File file = ["file.xml"]

        expect: "that the file does not exist"
        !file.exists()

        when: "the persistence manager attempts to load the file"
        persistenceManager.load file

        then: "an exception is thrown"
        def e = thrown(PersistenceException)
        e.message.contains "not found"
    }

    @Unroll
    def "Throw when persistence metadata is incorrect: version=#version, type=#type"() {
        given: "the xml file has not complete persistence metadata"
        def file = dictionaryFileToWrite
        writePersistenceMetadata(file, version, type)

        when: "the file is loaded"
        persistenceManager.load file

        then: "exception is thrown"
        def e = thrown(PersistenceException)
        e.message.contains messagePart

        where: "the parameters are"
        version | type   | messagePart
        null    | "xml"  | "Wrong version"
        "v1"    | null   | "Wrong type"
        "v123"  | "xml"  | "Wrong version"
        "v1"    | "html" | "Wrong type"
        "v123"  | "html" | "Wrong version"
    }

    private void writePersistenceMetadata(File file, version, type) {
        String metadata = "<persistenceManager" +
                (version == null ? "" : " version='$version'") +
                (type == null ? "" : " type='$type'") + " />"
        file << "<root>$metadata</root>"
    }
}
