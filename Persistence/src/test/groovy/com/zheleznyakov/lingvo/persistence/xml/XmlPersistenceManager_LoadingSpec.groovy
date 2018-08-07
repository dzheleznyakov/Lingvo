package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.persistence.xml.util.IOTestHelper
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import spock.lang.Specification
import spock.lang.Unroll

class XmlPersistenceManager_LoadingSpec extends Specification {
    private static final String PATH_TO_FILE_STORAGE = ZhConfigFactory.get().getString('persistence.xml.root')
    private XmlPersistenceManager persistenceManager
    private File file

    void setup() {
        persistenceManager = []
        file = dictionaryFile
    }

    void cleanup() {
        IOTestHelper.clean()
    }

    def "If xml file does not exist, throw"() {
        given: "the xml file"
        File file = ["$PATH_TO_FILE_STORAGE/file.xml"]

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
        given: "the xml file has no correct persistence metadata"
        def file = dictionaryFile
        writePersistenceMetadata(file, version, type)

        when: "the file is loaded"
        persistenceManager.load file

        then: "an exception is thrown"
        def e = thrown(PersistenceException)
        e.message.contains messagePart

        where: "the parameters are"
        version | type   || messagePart
        null    | "xml"  || "Wrong version"
        "v1"    | null   || "Wrong type"
        "v123"  | "xml"  || "Wrong version"
        "v1"    | "html" || "Wrong type"
        "v123"  | "html" || "Wrong version"
    }

    @Unroll
    def 'Throw when dictionary name or language is not persisted: name=#name, language=#language'() {
        given: 'the xml file has not dictionary name or language'
        def file = dictionaryFile
        writePersistenceMetadata(file, 'v1', 'xml')
        writeDictionaryRoot(file, name, language)

        when: 'the file is loaded'
        persistenceManager.load file

        then: 'an exception is thrown'
        PersistenceException e = thrown()
        e.message.contains messagePart
        e.message.contains 'does not exist'

        where: 'the parameters are'
        name        | language || messagePart
        'Test Name' | null     || 'language'
        null        | 'Fn'     || 'name'

    }

    private static File getDictionaryFile() {
        IOTestHelper.getDictionaryFileToWrite('Test')
    }

    private static void writePersistenceMetadata(File file, version, type) {
        String metadata = "<persistenceManager" +
                (version ? " version='$version'" : '') +
                (type ? " type='$type'" : '') + " />"
        file << "<root>$metadata</root>"
    }

    private static void writeDictionaryRoot(File file, name, language) {
        String s = '<dictionary>' +
                (name ? "<name>${name}</name>" : '') +
                (language ? "<language>${language}</language>" : '') +
                '</dictionary>'
        insertText(file, s, '</root>')
    }

    private static void insertText(File file, String textToInsert, String closingTag) {
        def newText = file.text
        int index = newText.indexOf(closingTag)
        newText = newText[0..index - 1] + textToInsert + newText.drop(index);
        file.text = newText
    }


}