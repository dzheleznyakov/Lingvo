package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.implementations.FakeEnglish
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import spock.lang.Specification

class XmlPersistenceManagerSpec extends Specification {

    def "Test basic properties of XmlPersistenceManager"() {
        given: "an empty dictionary"
        String dictionaryName = "Test"
        LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE, dictionaryName]

        and: "an XmlPersistenceManager1"
        XmlPersistenceManager persistenceManager = []

        expect: "that the xml file with the dictionary does not exist"
        String pathToFile = ZhConfigFactory.get().getString("persistence.xml.root")
        File file = new File("${pathToFile}/${dictionary.name}.xml")
        !file.exists()

        when: "the dictionary is persisted"
        persistenceManager.persist(dictionary)

        then: "the xml file with the dictionary exists"
        file.exists()
        assertFileContent(file)

        cleanup: "remove created file"
//        file.delete()
    }

    private assertFileContent(File file) {
        XmlSlurper xmlSlurper = []
        def xmlRoot = xmlSlurper.parse(file)
        true
    }
}
