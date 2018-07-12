package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.EnumXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.util.*
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.charset.StandardCharsets

class XmlReaderSpec extends Specification {
    private XmlReader reader = [ImmutableMap.of(TestEnum, TestEnumXmlDeserializer)]
    private static def testXmlGenerator = new TestXmlGenerator()

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
        xml                                                                                     | expectedClass   || value                    | expectedValue
        testXmlGenerator.booleanEntity('true')                                                  | BooleanEntity   || { it.getBooleanValue() } | true
        testXmlGenerator.booleanEntity('false')                                                 | BooleanEntity   || { it.getBooleanValue() } | false

        testXmlGenerator.charEntity('*')                                                        | CharEntity      || { it.getCharValue() }    | 42 as char
        testXmlGenerator.charEntity('1')                                                        | CharEntity      || { it.getCharValue() }    | '1'

        testXmlGenerator.byteEntity('42')                                                       | ByteEntity      || { it.getByteValue() }    | 42 as byte
        testXmlGenerator.byteEntity('1')                                                        | ByteEntity      || { it.getByteValue() }    | 1 as byte

        testXmlGenerator.shortEntity('42')                                                      | ShortEntity     || { it.getShortValue() }   | 42 as short
        testXmlGenerator.shortEntity('1')                                                       | ShortEntity     || { it.getShortValue() }   | 1 as short

        testXmlGenerator.integerEntity('42')                                                    | IntegerEntity   || { it.getIntValue() }     | 42
        testXmlGenerator.integerEntity('1')                                                     | IntegerEntity   || { it.getIntValue() }     | 1

        testXmlGenerator.longEntity('42')                                                       | LongEntity      || { it.getLongValue() }    | 42L
        testXmlGenerator.longEntity('1')                                                        | LongEntity      || { it.getLongValue() }    | 1L

        testXmlGenerator.floatEntity('42.0')                                                    | FloatEntity     || { it.getFloatValue() }   | 42f
        testXmlGenerator.floatEntity('1.0')                                                     | FloatEntity     || { it.getFloatValue() }   | 1f
        testXmlGenerator.floatEntity('1')                                                       | FloatEntity     || { it.getFloatValue() }   | 1f
        testXmlGenerator.floatEntity("1${Float.MAX_VALUE}")                                     | FloatEntity     || { it.getFloatValue() }   | Float.POSITIVE_INFINITY

        testXmlGenerator.doubleEntity('42.0')                                                   | DoubleEntity    || { it.getDoubleValue() }  | 42d
        testXmlGenerator.doubleEntity('1.0')                                                    | DoubleEntity    || { it.getDoubleValue() }  | 1d
        testXmlGenerator.doubleEntity('1')                                                      | DoubleEntity    || { it.getDoubleValue() }  | 1d
        testXmlGenerator.doubleEntity("1${Double.MAX_VALUE}")                                   | DoubleEntity    || { it.getDoubleValue() }  | Double.POSITIVE_INFINITY

        testXmlGenerator.enumEntity('FORTY_TWO')                                                | EnumEntity      || { it.getEnumValue() }    | TestEnum.FORTY_TWO
        testXmlGenerator.enumEntity('FORTY_THREE')                                              | EnumEntity      || { it.getEnumValue() }    | TestEnum.FORTY_THREE
        testXmlGenerator.enumEntity()                                                           | EnumEntity      || { it.getEnumValue() }    | null

        testXmlGenerator.stringEntity('testValue')                                              | StringEntity    || { it.getStringValue() }  | 'testValue'
        testXmlGenerator.stringEntity('testValue2')                                             | StringEntity    || { it.getStringValue() }  | 'testValue2'
        testXmlGenerator.stringEntity('')                                                       | StringEntity    || { it.getStringValue() }  | ''
        testXmlGenerator.stringEntity()                                                         | StringEntity    || { it.getStringValue() }  | null

        testXmlGenerator.listEntity(ArrayList, 42, 43, 44)                                      | ListEntity      || { it.getListValues() }   | [42, 43, 44]
        testXmlGenerator.listEntity(ArrayList, 0, 100, 999)                                     | ListEntity      || { it.getListValues() }   | [0, 100, 999]
        testXmlGenerator.listEntity(null)                                                       | ListEntity      || { it.getListValues() }   | null
        testXmlGenerator.listEntity(ArrayList)                                                  | ListEntity      || { it.getListValues() }   | []

        testXmlGenerator.setEntity(HashSet, 42d, 43d, 44d)                                      | SetEntity       || { it.getSetValues() }    | [42d, 43d, 44d].toSet()
        testXmlGenerator.setEntity(HashSet, 42.42d, 43.43d, 44.44d)                             | SetEntity       || { it.getSetValues() }       | [42.42, 43.43, 44.44].toSet()
        testXmlGenerator.setEntity(null)                                                        | SetEntity       || { it.getSetValues() }       | null
        testXmlGenerator.setEntity(HashSet)                                                     | SetEntity       || { it.getSetValues() }       | [].toSet()

        testXmlGenerator.setObjectEntity(ImmutableSet, new IntegerEntity(), new DoubleEntity()) | SetObjectEntity || { it.getSetObjectValues() } | [new IntegerEntity(), new DoubleEntity()].toSet()

        testXmlGenerator.mapEntity(HashMap, 42, true, [42d], new BooleanEntity())               | MapEntity       || { it.getMyMap() }           | [42: true, [42D]: new BooleanEntity()]
        testXmlGenerator.mapEntity(ImmutableMap)                                                | MapEntity       || { it.getMyMap() }           | [:]
    }

    @Unroll
    def "If xml contains illegal value, throw (#clazz.simpleName)"() {
        given: "the file contains an entity encoded in xml"
        InputStream input = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))

        when: "the reader reads the file"
        reader.read(input, clazz)

        then: "the extracted entity is correct"
        IllegalArgumentException exception = thrown()
        exception.message.matches ".*$expectedExceptionMessage.*"

        where: "the parameters are"
        xml                                                     | clazz         || expectedExceptionMessage
        testXmlGenerator.booleanEntity()                        | BooleanEntity || 'does not exist'
        testXmlGenerator.booleanEntity('blah')                  | BooleanEntity || 'not a boolean'

        testXmlGenerator.charEntity()                           | CharEntity    || 'does not exist'
        testXmlGenerator.charEntity('')                         | CharEntity    || 'no character'
        testXmlGenerator.charEntity('42')                       | CharEntity    || 'not a character'

        testXmlGenerator.byteEntity()                           | ByteEntity    || 'does not exist'
        testXmlGenerator.byteEntity('')                         | ByteEntity    || 'no byte'
        testXmlGenerator.byteEntity("1${Byte.MAX_VALUE}")       | ByteEntity    || 'not a byte'
        testXmlGenerator.byteEntity('a')                        | ByteEntity    || 'not a byte'
        testXmlGenerator.byteEntity('42.0')                     | ByteEntity    || 'not a byte'

        testXmlGenerator.shortEntity()                          | ShortEntity   || 'does not exist'
        testXmlGenerator.shortEntity('')                        | ShortEntity   || 'no short'
        testXmlGenerator.shortEntity("1${Short.MAX_VALUE}")     | ShortEntity   || 'not a short'
        testXmlGenerator.shortEntity('short')                   | ShortEntity   || 'not a short'
        testXmlGenerator.shortEntity('42.0')                    | ShortEntity   || 'not a short'

        testXmlGenerator.integerEntity()                        | IntegerEntity || 'does not exist'
        testXmlGenerator.integerEntity('')                      | IntegerEntity || 'no integer'
        testXmlGenerator.integerEntity("1${Integer.MAX_VALUE}") | IntegerEntity || 'not a integer'
        testXmlGenerator.integerEntity('int')                   | IntegerEntity || 'not a integer'
        testXmlGenerator.integerEntity('42.0')                  | IntegerEntity || 'not a integer'

        testXmlGenerator.longEntity()                           | LongEntity    || 'does not exist'
        testXmlGenerator.longEntity('')                         | LongEntity    || 'no long'
        testXmlGenerator.longEntity("1${Long.MAX_VALUE}")       | LongEntity    || 'not a long'
        testXmlGenerator.longEntity('long')                     | LongEntity    || 'not a long'
        testXmlGenerator.longEntity('42.0')                     | LongEntity    || 'not a long'

        testXmlGenerator.floatEntity()                          | FloatEntity   || 'does not exist'
        testXmlGenerator.floatEntity('')                        | FloatEntity   || 'no float'
        testXmlGenerator.floatEntity('float')                   | FloatEntity   || 'not a float'

        testXmlGenerator.doubleEntity()                         | DoubleEntity  || 'does not exist'
        testXmlGenerator.doubleEntity('')                       | DoubleEntity  || 'no double'
        testXmlGenerator.doubleEntity('double')                 | DoubleEntity  || 'not a double'

        testXmlGenerator.enumEntity('')                         | EnumEntity    || 'no enum value'
        testXmlGenerator.enumEntity('FORTY_FOUR')               | EnumEntity    || 'No enum constant.*TestEnum.*FORTY_FOUR'
    }

    private static trait TestEnumXmlDeserializer implements EnumXmlDeserializer<TestEnum> {}

}
