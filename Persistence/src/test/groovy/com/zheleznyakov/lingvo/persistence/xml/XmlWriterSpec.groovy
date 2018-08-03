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
        entity                     || expectedOutput
        new BooleanArrayEntity()   || testXmlGenerator.booleanArrayEntity('true', 'true','false')
        new CharacterArrayEntity() || testXmlGenerator.characterArrayEntity('a', 'b', 'c')
        new ByteArrayEntity()      || testXmlGenerator.byteArrayEntity('42', '43', '44')
        new ShortArrayEntity()     || testXmlGenerator.shortArrayEntity('42', '43', '44')
        new IntegerArrayEntity()   || testXmlGenerator.integerArrayEntity('42', '43', '44')
        new LongArrayEntity()      || testXmlGenerator.longArrayEntity('42', '43', '44')
        new FloatArrayEntity()     || testXmlGenerator.floatArrayEntity('42.0', '43.0', '44.0')
        new DoubleArrayEntity()    || testXmlGenerator.doubleArrayEntity('42.0', '43.0', '44.0')

        new ObjectArrayEntity(Integer, 42, 43, 44)                        || testXmlGenerator.objectArrayEntity('Integer', '42', '43', '44')
        new ObjectArrayEntity(Integer)                                            || testXmlGenerator.objectArrayEntity('Integer')
        new ObjectArrayEntity(TestEnum, TestEnum.FORTY_TWO, TestEnum.FORTY_THREE) || testXmlGenerator.objectArrayEntity('TestEnum', 'FORTY_TWO', 'FORTY_THREE')

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
