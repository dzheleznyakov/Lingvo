package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.BooleanEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.CharEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.ByteEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.ShortEntity
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.charset.StandardCharsets

class XmlReaderSpec extends Specification {
    private XmlReader reader = []
    private File file = ["${ZhConfigFactory.get().getString("persistence.xml.root")}/temp.xml"]

    def cleanup() {
        file.delete()
    }

    @Unroll
    def "Test reading entity: #expectedClass.simpleName, value=#expectedValue"() {
        given: "the file contains an entity encoded in xml"
        InputStream input = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))

        when: "the reader reads the file"
        def entity = reader.read(input, expectedClass)

        then: "the extracted entity is correct"
        entity.getClass() == expectedClass
        value(entity) == expectedValue

        where: "the parameters are"
        xml                                                                 | expectedClass       || value                    | expectedValue
        "<BooleanEntity><booleanValue>true</booleanValue></BooleanEntity>"  | BooleanEntity.class || { it.getBooleanValue() } | true
        "<BooleanEntity><booleanValue>false</booleanValue></BooleanEntity>" | BooleanEntity.class || { it.getBooleanValue() } | false
        "<CharEntity><charValue>*</charValue></CharEntity>"                 | CharEntity.class    || { it.getCharValue() }    | 42 as char
        "<CharEntity><charValue>1</charValue></CharEntity>"                 | CharEntity.class    || { it.getCharValue() }    | '1'
        "<ByteEntity><byteValue>42</byteValue></ByteEntity>"                | ByteEntity.class    || { it.getByteValue() }    | 42 as byte
        "<ByteEntity><byteValue>1</byteValue></ByteEntity>"                 | ByteEntity.class    || { it.getByteValue() }    | 1 as byte
        "<ShortEntity><shortValue>42</shortValue></ShortEntity>"            | ShortEntity.class   || { it.getShortValue() }   | 42 as short
        "<ShortEntity><shortValue>1</shortValue></ShortEntity>"             | ShortEntity.class   || { it.getShortValue() }   | 1 as short
    }

    @Unroll
    def "If xml contains illegal value, throw (#clazz.simpleName)"() {
        given: "the file contains an entity encoded in xml"
        InputStream input = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))

        when: "the reader reads the file"
        reader.read(input, clazz)

        then: "the extracted entity is correct"
        IllegalArgumentException exception = thrown()
        exception.message.contains expectedExceptionMessage
        println exception.message

        where: "the parameters are"
        xml                                                                            | clazz               || expectedExceptionMessage
        "<BooleanEntity><booleanValue>blah</booleanValue></BooleanEntity>"             | BooleanEntity.class || "not a boolean"

        "<CharEntity><charValue></charValue></CharEntity>"                             | CharEntity.class    || "no character"
        "<CharEntity><charValue>42</charValue></CharEntity>"                           | CharEntity.class    || "not a character"

        "<ByteEntity><byteValue/></ByteEntity>"                                        | ByteEntity.class    || "no byte"
        "<ByteEntity><byteValue>${Byte.MAX_VALUE + "0"}</byteValue></ByteEntity>"      | ByteEntity.class    || "not a byte"
        "<ByteEntity><byteValue>a</byteValue></ByteEntity>"                            | ByteEntity.class    || "not a byte"
        "<ByteEntity><byteValue>42.0</byteValue></ByteEntity>"                         | ByteEntity.class    || "not a byte"

        "<ShortEntity><shortValue></shortValue></ShortEntity>"                         | ShortEntity.class   || "no short"
        "<ShortEntity><shortValue>${Short.MAX_VALUE + "0"}</shortValue></ShortEntity>" | ShortEntity.class   || "not a short"
        "<ShortEntity><shortValue>short</shortValue></ShortEntity>"                    | ShortEntity.class   || "not a short"
        "<ShortEntity><shortValue>42.0</shortValue></ShortEntity>"                     | ShortEntity.class   || "not a short"
    }

}
