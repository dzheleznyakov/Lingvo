package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.TestEnum
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.BooleanEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.ByteEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.CharEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.DoubleEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.EnumEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.FloatEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.ByteArrayEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.ShortArrayEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.IntArrayEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.LongArrayEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.BooleanArrayEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.CharArrayEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.FloatArrayEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.DoubleArrayEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.ObjectArrayEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.IntegerEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.ListEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.LongEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.MapEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.SetEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.SetObjectEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.ShortEntity
import com.zheleznyakov.lingvo.persistence.xml.util.TestClasses.StringEntity
import spock.lang.Specification
import spock.lang.Unroll

class XmlWriterSpec extends Specification {
    private StringWriter output = []
    private XmlWriter writer = XmlWriter.with(output)

    @Unroll
    def "Test persisting entity: #entity.class.simpleName"() {
        when: "the entity is persisted"
        writer.write(entity)

        then: "only annotated fields are persisted"
        trimLines(output.toString()) == trimLines(expectedOutput).replace("><", ">\n<")

        where: "the parameters are"
        entity                        || expectedOutput
        new BooleanEntity()           || '<BooleanEntity><booleanValue>true</booleanValue></BooleanEntity>'
        new ShortEntity()             || '<ShortEntity><shortValue>42</shortValue></ShortEntity>'
        new CharEntity()              || '<CharEntity><charValue>*</charValue></CharEntity>'
        new ByteEntity()              || '<ByteEntity><byteValue>42</byteValue></ByteEntity>'
        new IntegerEntity()           || '<IntegerEntity><intValue>42</intValue></IntegerEntity>'
        new LongEntity()              || '<LongEntity><longValue>42</longValue></LongEntity>'
        new FloatEntity()             || '<FloatEntity><floatValue>42.0</floatValue></FloatEntity>'
        new DoubleEntity()            || '<DoubleEntity><doubleValue>42.0</doubleValue></DoubleEntity>'
        new EnumEntity()              || '<EnumEntity><enumValue>FORTY_TWO</enumValue></EnumEntity>'
        new StringEntity()            || '<StringEntity><stringValue>testValue</stringValue></StringEntity>'
        new ListEntity()              || """<ListEntity>
                                              <listValues type='java.util.ArrayList'>
                                                <elem type='java.lang.Integer'>42</elem>
                                                <elem type='java.lang.Integer'>43</elem>
                                                <elem type='java.lang.Integer'>44</elem>
                                              </listValues>
                                            </ListEntity>"""
        new SetEntity()               || """<SetEntity>
                                              <setValues type='java.util.HashSet'>
                                                <elem type='java.lang.Double'>42.0</elem>
                                                <elem type='java.lang.Double'>43.0</elem>
                                                <elem type='java.lang.Double'>44.0</elem>
                                              </setValues>
                                            </SetEntity>"""
        new SetObjectEntity()         || """<SetObjectEntity>
                                              <objectValues type='com.google.common.collect.ImmutableSet'>
                                                <elem type='com.zheleznyakov.lingvo.persistence.xml.util.TestClasses\$IntegerEntity'><intValue>42</intValue></elem>
                                                <elem type='com.zheleznyakov.lingvo.persistence.xml.util.TestClasses\$DoubleEntity'><doubleValue>42.0</doubleValue></elem>
                                              </objectValues>
                                            </SetObjectEntity>"""
        new MapEntity()               || """<MapEntity>
                                              <myMap>
                                                <entry>
                                                  <Integer>42</Integer>
                                                  <Boolean>true</Boolean>
                                                </entry>
                                                <entry>
                                                  <ArrayList type='java.util.ArrayList'><elem type='java.lang.Double'>42.0</elem></ArrayList>
                                                  <BooleanEntity><booleanValue>true</booleanValue></BooleanEntity>
                                                </entry>
                                              </myMap>
                                            </MapEntity>"""
    }

    @Unroll
    def "Test persisting array: #entity.class.simpleName"() {
        when: "the entity is persisted"
        writer.write(entity)

        then: "only annotated fields are persisted"
        trimLines(output.toString()) == expectedOutput.replace("><", ">\n<")

        where: "the parameters are"
        entity                   || expectedOutput
        new BooleanArrayEntity() || '<BooleanArrayEntity><arrayValues><Boolean>true</Boolean><Boolean>true</Boolean><Boolean>false</Boolean></arrayValues></BooleanArrayEntity>'
        new CharArrayEntity()    || '<CharArrayEntity><arrayValues><Character>a</Character><Character>b</Character><Character>c</Character></arrayValues></CharArrayEntity>'
        new ByteArrayEntity()    || '<ByteArrayEntity><arrayValues><Byte>42</Byte><Byte>43</Byte><Byte>44</Byte></arrayValues></ByteArrayEntity>'
        new ShortArrayEntity()   || '<ShortArrayEntity><arrayValues><Short>42</Short><Short>43</Short><Short>44</Short></arrayValues></ShortArrayEntity>'
        new IntArrayEntity()     || '<IntArrayEntity><arrayValues><Integer>42</Integer><Integer>43</Integer><Integer>44</Integer></arrayValues></IntArrayEntity>'
        new LongArrayEntity()    || '<LongArrayEntity><arrayValues><Long>42</Long><Long>43</Long><Long>44</Long></arrayValues></LongArrayEntity>'
        new FloatArrayEntity()   || '<FloatArrayEntity><arrayValues><Float>42.0</Float><Float>43.0</Float><Float>44.0</Float></arrayValues></FloatArrayEntity>'
        new DoubleArrayEntity()  || '<DoubleArrayEntity><arrayValues><Double>42.0</Double><Double>43.0</Double><Double>44.0</Double></arrayValues></DoubleArrayEntity>'

        new ObjectArrayEntity<Integer>(42, 43, 44)                        || '<ObjectArrayEntity><arrayValues><Integer>42</Integer><Integer>43</Integer><Integer>44</Integer></arrayValues></ObjectArrayEntity>'
        new ObjectArrayEntity<TestEnum>(TestEnum.FORTY_TWO, TestEnum.FORTY_THREE) || '<ObjectArrayEntity><arrayValues><TestEnum>FORTY_TWO</TestEnum><TestEnum>FORTY_THREE</TestEnum></arrayValues></ObjectArrayEntity>'

    }

    private static String trimLines(String text) {
        Arrays.stream(text.split("\n"))
                .map { it.trim() }
                .toArray()
                .join("\n")
    }
}
