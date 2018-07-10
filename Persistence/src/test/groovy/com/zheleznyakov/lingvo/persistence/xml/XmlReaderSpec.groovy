package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableMap
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.EnumXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.BooleanEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.ByteEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.CharEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.DoubleEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.EnumEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.FloatEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.IntegerEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.ListEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.LongEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.ShortEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.StringEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.SetEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.SetObjectEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.MapEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.TestEnum
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.charset.StandardCharsets

class XmlReaderSpec extends Specification {
    private XmlReader reader = [ImmutableMap.of(TestEnum, TestEnumXmlDeserializer)]

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
        xml                                                                            | expectedClass  || value                    | expectedValue
        '<BooleanEntity><booleanValue>true</booleanValue></BooleanEntity>'             | BooleanEntity  || { it.getBooleanValue() } | true
        '<BooleanEntity><booleanValue>false</booleanValue></BooleanEntity>'            | BooleanEntity  || { it.getBooleanValue() } | false

        '<CharEntity><charValue>*</charValue></CharEntity>'                            | CharEntity     || { it.getCharValue() }    | 42 as char
        '<CharEntity><charValue>1</charValue></CharEntity>'                            | CharEntity     || { it.getCharValue() }    | '1'

        '<ByteEntity><byteValue>42</byteValue></ByteEntity>'                           | ByteEntity     || { it.getByteValue() }    | 42 as byte
        '<ByteEntity><byteValue>1</byteValue></ByteEntity>'                            | ByteEntity     || { it.getByteValue() }    | 1 as byte

        '<ShortEntity><shortValue>42</shortValue></ShortEntity>'                       | ShortEntity    || { it.getShortValue() }   | 42 as short
        '<ShortEntity><shortValue>1</shortValue></ShortEntity>'                        | ShortEntity    || { it.getShortValue() }   | 1 as short

        '<IntegerEntity><intValue>42</intValue></IntegerEntity>'                       | IntegerEntity  || { it.getIntValue() }     | 42
        '<IntegerEntity><intValue>1</intValue></IntegerEntity>'                        | IntegerEntity  || { it.getIntValue() }     | 1

        '<LongEntity><longValue>42</longValue></LongEntity>'                           | LongEntity     || { it.getLongValue() }    | 42L
        '<LongEntity><longValue>1</longValue></LongEntity>'                            | LongEntity     || { it.getLongValue() }    | 1L

        '<FloatEntity><floatValue>42.0</floatValue></FloatEntity>'                     | FloatEntity    || { it.getFloatValue() }   | 42f
        '<FloatEntity><floatValue>1.0</floatValue></FloatEntity>'                      | FloatEntity    || { it.getFloatValue() }   | 1f
        '<FloatEntity><floatValue>1</floatValue></FloatEntity>'                        | FloatEntity    || { it.getFloatValue() }   | 1f
        "<FloatEntity><floatValue>1${Float.MAX_VALUE}</floatValue></FloatEntity>"      | FloatEntity    || { it.getFloatValue() }   | Float.POSITIVE_INFINITY

        '<DoubleEntity><doubleValue>42.0</doubleValue></DoubleEntity>'                 | DoubleEntity   || { it.getDoubleValue() }  | 42d
        '<DoubleEntity><doubleValue>1.0</doubleValue></DoubleEntity>'                  | DoubleEntity   || { it.getDoubleValue() }  | 1d
        '<DoubleEntity><doubleValue>1</doubleValue></DoubleEntity>'                    | DoubleEntity   || { it.getDoubleValue() }  | 1d
        "<DoubleEntity><doubleValue>1${Double.MAX_VALUE}</doubleValue></DoubleEntity>" | DoubleEntity   || { it.getDoubleValue() }  | Double.POSITIVE_INFINITY

        '<EnumEntity><enumValue>FORTY_TWO</enumValue></EnumEntity>'                    | EnumEntity     || { it.getEnumValue() }    | TestEnum.FORTY_TWO
        '<EnumEntity><enumValue>FORTY_THREE</enumValue></EnumEntity>'                  | EnumEntity     || { it.getEnumValue() }    | TestEnum.FORTY_THREE
        '<EnumEntity/>'                                                                | EnumEntity     || { it.getEnumValue() }    | null

        '<StringEntity><stringValue>testValue</stringValue></StringEntity>'            | StringEntity   || { it.getStringValue() }  | 'testValue'
        '<StringEntity><stringValue>testValue2</stringValue></StringEntity>'           | StringEntity   || { it.getStringValue() }  | 'testValue2'
        '<StringEntity><stringValue/></StringEntity>'                                  | StringEntity   || { it.getStringValue() }  | ''
        '<StringEntity></StringEntity>'                                                | StringEntity   || { it.getStringValue() }  | null

        """<ListEntity>
             <listValues type='java.util.ArrayList'>
               <elem type='java.lang.Integer'>42</elem>
               <elem type='java.lang.Integer'>43</elem>
               <elem type='java.lang.Integer'>44</elem>
             </listValues>
           </ListEntity>"""                                                            | ListEntity     || { it.getListValues() }   | [42, 43, 44]

        """<ListEntity>
             <listValues type='java.util.ArrayList'>
               <elem type='java.lang.Integer'>0</elem>
               <elem type='java.lang.Integer'>100</elem>
               <elem type='java.lang.Integer'>999</elem>
             </listValues>
           </ListEntity>"""                                                            | ListEntity     || { it.getListValues() }   | [0, 100, 999]

        '<ListEntity/>'                                                                | ListEntity     || { it.getListValues() }   | null

        """<ListEntity>
             <listValues type='java.util.ArrayList' />
           </ListEntity>"""                                                            | ListEntity     || { it.getListValues() }   | []

        """<SetEntity>
             <setValues type='java.util.HashSet'>
               <elem type='java.lang.Double'>42.0</elem>
               <elem type='java.lang.Double'>43.0</elem>
               <elem type='java.lang.Double'>44.0</elem>
             </setValues>
           </SetEntity>"""                                                             | SetEntity      || { it.getSetValues() }    | [42d, 43d, 44d].toSet()

        """<SetEntity>
             <setValues type='java.util.HashSet'>
               <elem type='java.lang.Double'>42.42</elem>
               <elem type='java.lang.Double'>43.43</elem>
               <elem type='java.lang.Double'>44.44</elem>
             </setValues>
           </SetEntity>"""                                                             | SetEntity      || { it.getSetValues() }    | [42.42, 43.43, 44.44].toSet()

        '<SetEntity/>'                                                                 | SetEntity      || { it.getSetValues() }    | null

        """<SetEntity>
             <setValues type='java.util.HashSet'/>
           </SetEntity>"""                                                             | SetEntity      || { it.getSetValues() }    | [].toSet()

        """<SetObjectEntity>
             <objectValues type='com.google.common.collect.ImmutableSet'>
               <elem type='com.zheleznyakov.lingvo.persistence.xml.util.TestClasses\$IntegerEntity'><intValue>42</intValue></elem>
               <elem type='com.zheleznyakov.lingvo.persistence.xml.util.TestClasses\$DoubleEntity'><doubleValue>42.0</doubleValue></elem>
             </objectValues>
           </SetObjectEntity>"""                                                       | SetObjectEntity || { it.getObjectValues() } | [new IntegerEntity(), new DoubleEntity()].toSet()

//        """<MapEntity>
//             <myMap>
//               <entry>
//                 <Integer>42</Integer>
//                 <Boolean>true</Boolean>
//               </entry>
//               <entry>
//                 <ArrayList type='java.util.ArrayList'><elem type='java.lang.Double'>42.0</elem></ArrayList>
//                 <BooleanEntity><booleanValue>true</booleanValue></BooleanEntity>
//               </entry>
//             </myMap>
//           </MapEntity>"""                                                             | MapEntity       || { it.getMyMap() }        | [42: true, 42D: new BooleanEntity()]
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
        xml                                                                         | clazz         || expectedExceptionMessage
        '<BooleanEntity></BooleanEntity>'                                           | BooleanEntity || 'does not exist'
        '<BooleanEntity><booleanValue>blah</booleanValue></BooleanEntity>'          | BooleanEntity || 'not a boolean'

        '<CharEntity></CharEntity>'                                                 | CharEntity    || 'does not exist'
        '<CharEntity><charValue></charValue></CharEntity>'                          | CharEntity    || 'no character'
        '<CharEntity><charValue>42</charValue></CharEntity>'                        | CharEntity    || 'not a character'

        '<ByteEntity></ByteEntity>'                                                 | ByteEntity    || 'does not exist'
        '<ByteEntity><byteValue/></ByteEntity>'                                     | ByteEntity    || 'no byte'
        "<ByteEntity><byteValue>1${Byte.MAX_VALUE}</byteValue></ByteEntity>"        | ByteEntity    || 'not a byte'
        '<ByteEntity><byteValue>a</byteValue></ByteEntity>'                         | ByteEntity    || 'not a byte'
        '<ByteEntity><byteValue>42.0</byteValue></ByteEntity>'                      | ByteEntity    || 'not a byte'

        '<ShortEntity></ShortEntity>'                                               | ShortEntity   || 'does not exist'
        '<ShortEntity><shortValue></shortValue></ShortEntity>'                      | ShortEntity   || 'no short'
        "<ShortEntity><shortValue>1${Short.MAX_VALUE}</shortValue></ShortEntity>"   | ShortEntity   || 'not a short'
        '<ShortEntity><shortValue>short</shortValue></ShortEntity>'                 | ShortEntity   || 'not a short'
        '<ShortEntity><shortValue>42.0</shortValue></ShortEntity>'                  | ShortEntity   || 'not a short'

        '<IntegerEntity></IntegerEntity>'                                           | IntegerEntity || 'does not exist'
        '<IntegerEntity><intValue></intValue></IntegerEntity>'                      | IntegerEntity || 'no integer'
        "<IntegerEntity><intValue>1${Integer.MAX_VALUE}</intValue></IntegerEntity>" | IntegerEntity || 'not a integer'
        '<IntegerEntity><intValue>int</intValue></IntegerEntity>'                   | IntegerEntity || 'not a integer'
        '<IntegerEntity><intValue>42.0</intValue></IntegerEntity>'                  | IntegerEntity || 'not a integer'

        '<LongEntity></LongEntity>'                                                 | LongEntity    || 'does not exist'
        '<LongEntity><longValue></longValue></LongEntity>'                          | LongEntity    || 'no long'
        "<LongEntity><longValue>1${Long.MAX_VALUE}</longValue></LongEntity>"        | LongEntity    || 'not a long'
        '<LongEntity><longValue>long</longValue></LongEntity>'                      | LongEntity    || 'not a long'
        '<LongEntity><longValue>42.0</longValue></LongEntity>'                      | LongEntity    || 'not a long'

        '<FloatEntity/>'                                                            | FloatEntity   || 'does not exist'
        '<FloatEntity><floatValue/></FloatEntity>'                                  | FloatEntity   || 'no float'
        '<FloatEntity><floatValue>float</floatValue></FloatEntity>'                 | FloatEntity   || 'not a float'

        '<DoubleEntity/>'                                                           | DoubleEntity  || 'does not exist'
        '<DoubleEntity><doubleValue/></DoubleEntity>'                               | DoubleEntity  || 'no double'
        '<DoubleEntity><doubleValue>float</doubleValue></DoubleEntity>'             | DoubleEntity  || 'not a double'

        '<EnumEntity><enumValue/></EnumEntity>'                                     | EnumEntity    || 'no enum value'
        '<EnumEntity><enumValue>FORTY_FOUR</enumValue></EnumEntity>'                | EnumEntity    || 'No enum constant.*TestEnum.*FORTY_FOUR'
    }

    private static trait TestEnumXmlDeserializer implements EnumXmlDeserializer<TestEnum> {}

}
