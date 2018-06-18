package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.BooleanEntity
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import org.junit.Ignore
import spock.lang.Specification

@Ignore
class XmlReaderSpec extends Specification {
    private XmlReader reader = []
    private File file = ["${ZhConfigFactory.get().getString("persistence.xml.root")}/temp.xml"]

    def cleanup() {
        file.delete()
    }

    def "Test reading entity"() {
        given: "the file contains an entity encoded in xml"
        file << text

        when: "the reader reads the file"
        def entity = reader.read(file)

        then: "the extracted entity is correct"
        entity.getClass() == expectedEntity.getClass()

        where: "the parameters are"
        text                                                                | expectedEntity
        "<BooleanEntity><booleanValue>true</booleanValue></BooleanEntity>"  | new BooleanEntity()
    }

    def "temp"() {
        given:
        def name = LearningDictionary.class.getDeclaredClasses()[0].name
        Class aClass = Class.forName(name)
        println aClass
    }

}
