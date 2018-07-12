package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.persistence.xml.util.*
import spock.lang.Specification
import spock.lang.Unroll

class XmlWriterSpec extends Specification {
    private StringWriter output = []
    private XmlWriter writer = XmlWriter.with(output)
    private static def testXmlGenerator = new TestXmlGenerator()

    def setupSpec() {
        String.metaClass.trimLines { -> trimLines(delegate) }
    }

    def setup() {
        output.metaClass.trimLines { -> XmlWriterSpec.trimLines(delegate.toString()) }
    }

    @Unroll
    def "Test persisting entity: #entity.class.simpleName"() {
        when: "the entity is persisted"
        writer.write(entity)

        then: "only annotated fields are persisted"
        output.trimLines() == expectedOutput.trimLines().replace("><", ">\n<")

        where: "the parameters are"
        entity                || expectedOutput
        new BooleanEntity()   || testXmlGenerator.booleanEntity('true')
        new CharEntity()      || testXmlGenerator.charEntity('*')
        new ByteEntity()      || testXmlGenerator.byteEntity('42')
        new ShortEntity()     || testXmlGenerator.shortEntity('42')
        new IntegerEntity()   || testXmlGenerator.integerEntity('42')
        new LongEntity()      || testXmlGenerator.longEntity('42')
        new FloatEntity()     || testXmlGenerator.floatEntity('42.0')
        new DoubleEntity()    || testXmlGenerator.doubleEntity('42.0')
        new EnumEntity()      || testXmlGenerator.enumEntity('FORTY_TWO')
        new StringEntity()    || testXmlGenerator.stringEntity('testValue')
        new ListEntity()      || testXmlGenerator.listEntity(ArrayList, 42, 43, 44)
        new SetEntity()       || testXmlGenerator.setEntity(HashSet, 42D, 43D, 44D)
        new SetObjectEntity() || testXmlGenerator.setObjectEntity(ImmutableSet, new IntegerEntity(), new DoubleEntity())
        new MapEntity()       || testXmlGenerator.mapEntity(ImmutableMap, 42, true, [42d], new BooleanEntity())
    }

    @Unroll
    def "Test persisting array: #entity.class.simpleName"() {
        when: "the entity is persisted"
        writer.write(entity)

        then: "only annotated fields are persisted"
        output.trimLines() == expectedOutput.replace("><", ">\n<")

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

    @Unroll
    def "Test persisting maps: #entity.class.simpleName"() {
        when: "the map is persisted"
        writer.write(entity)

        then: "the map type is persisted correctly"
        output.trimLines() == expectedOutput.trimLines().replace("><", ">\n<")

        where: "the parameters are"
        entity                          || expectedOutput
        new HashMap()                   || "<HashMap type='java.util.HashMap' />"
        new MapEntity().setMyMap([a:1]) || testXmlGenerator.mapEntity(LinkedHashMap, 'a', 1)
    }

    @Unroll
    def "Test serialization of container classes: #container.class.simpleName"() {
        when: "a (empty) container is serialised"
        writer.write(container)

        then: "its type is written correctly"
        output.trimLines() == expectedXml.trimLines()

        where: "the parameters are"
        container           || expectedXml
        new HashSet()       || testXmlGenerator.empty(HashSet)
        new LinkedHashSet() || testXmlGenerator.empty(LinkedHashSet)
        ImmutableSet.of()   || testXmlGenerator.emptyRegularImmutableSet(ImmutableSet)
        new ArrayList()     || testXmlGenerator.empty(ArrayList)
        new LinkedList()    || testXmlGenerator.empty(LinkedList)
        ImmutableList.of()  || testXmlGenerator.emptyRegularImmutableList(ImmutableList)
        new HashMap()       || testXmlGenerator.empty(HashMap)
        new LinkedHashMap() || testXmlGenerator.empty(LinkedHashMap)
        ImmutableMap.of()   || testXmlGenerator.emptyRegularImmutableMap(ImmutableMap)
    }

    private static String trimLines(String text) {
        Arrays.stream(text.split("\n"))
                .map { it.trim() }
                .toArray()
                .join("\n")
    }
}
