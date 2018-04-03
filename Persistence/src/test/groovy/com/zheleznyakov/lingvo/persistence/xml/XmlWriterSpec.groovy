package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.persistence.Persistable
import spock.lang.Specification
import spock.lang.Unroll

class XmlWriterSpec extends Specification {
    private StringWriter output = []
    private XmlWriter writer = [output]

    @Unroll
    def "Test persisting entity: #entity.class.simpleName"() {
        when: "the entity is persisted"
        writer.write(entity)

        then: "only annotated fields are persisted"
        output.toString().replace(" ", "") == expectedOutput.replace("><", ">\n<")

        where: "the parameters are"
        entity                || expectedOutput
        new BooleanEntity()   || "<BooleanEntity><booleanValue>true</booleanValue></BooleanEntity>"
        new ShortEntity()     || "<ShortEntity><shortValue>42</shortValue></ShortEntity>"
        new CharEntity()      || "<CharEntity><charValue>*</charValue></CharEntity>"
        new ByteEntity()      || "<ByteEntity><byteValue>42</byteValue></ByteEntity>"
        new IntegerEntity()   || "<IntegerEntity><intValue>42</intValue></IntegerEntity>"
        new LongEntity()      || "<LongEntity><longValue>42</longValue></LongEntity>"
        new FloatEntity()     || "<FloatEntity><floatValue>42.0</floatValue></FloatEntity>"
        new DoubleEntity()    || "<DoubleEntity><doubleValue>42.0</doubleValue></DoubleEntity>"
        new EnumEntity()      || "<EnumEntity><enumValue>FOURTY_TWO</enumValue></EnumEntity>"
        new StringEntity()    || "<StringEntity><stringValue>testValue</stringValue></StringEntity>"
        new ArrayEntity()     || "<ArrayEntity><arrayValues><Integer>42</Integer><Integer>43</Integer><Integer>44</Integer></arrayValues></ArrayEntity>"
        new ListEntity()      || "<ListEntity><listValues><Integer>42</Integer><Integer>43</Integer><Integer>44</Integer></listValues></ListEntity>"
        new SetEntity()       || "<SetEntity><setValues><Double>42.0</Double><Double>43.0</Double><Double>44.0</Double></setValues></SetEntity>"
        new SetObjectEntity() || "<SetObjectEntity><objectValues>" +
                                 "<IntegerEntity><intValue>42</intValue></IntegerEntity>" +
                                 "<DoubleEntity><doubleValue>42.0</doubleValue></DoubleEntity>" +
                                 "</objectValues></SetObjectEntity>"
        new MapEntity()       || "<MapEntity><myMap>" +
                                 "<entry><Integer>42</Integer><Boolean>true</Boolean></entry>" +
                                 "<entry><ArrayList><Double>42.0</Double></ArrayList><BooleanEntity><booleanValue>true</booleanValue></BooleanEntity></entry>" +
                                 "</myMap></MapEntity>"
    }

    private static class BooleanEntity {
        @Persistable private boolean booleanValue = true
        private boolean booleanValue2 = false
    }

    private static class ShortEntity {
        @Persistable private short shortValue = 42 as short
        private short shortValue2 = (Math.random() * 100) as short
    }

    private static class CharEntity {
        @Persistable private char charValue = 42 as char
        private char charValue2 = (Math.random() * 100) as char
    }

    private static class ByteEntity {
        @Persistable private byte byteValue = 42 as byte
        private byte byteValue2 = (Math.random() * 100) as byte
    }

    private static class IntegerEntity {
        @Persistable private int intValue = 42
        private int intValue2 = (Math.random() * 100) as int
    }

    private static class LongEntity {
        @Persistable private long longValue = 42L
        private long longValue2 = Math.random() * 100L;
    }

    private static class FloatEntity {
        @Persistable private float floatValue = 42F
        private float floatValue2 = (Math.random() * 100) as float
    }

    private static class DoubleEntity {
        @Persistable private double doubleValue = 42D
        private double doubleValue2 = Math.random() * 100
    }

    private static class EnumEntity {
        @Persistable private TestEnum enumValue = TestEnum.FOURTY_TWO
        private TestEnum enumValue2 = TestEnum.FOURTY_THREE

    }

    private static class StringEntity {
        @Persistable private String stringValue = "testValue"
        private String stringValue2 = "another test value"
    }

    private static class ArrayEntity {
        @Persistable private int[] arrayValues = [42, 43, 44].toArray()
        private double[] arrayValues2 = [45D, 46D, 47D].toArray()
    }

    private static class ListEntity {
        @Persistable private List<Integer> listValues = ImmutableList.of(42, 43, 44)
        private List<Double> listValues2 = ImmutableList.of(45D, 46D, 47D)
    }

    private static class SetEntity {
        @Persistable private Set<Double> setValues = ImmutableSet.of(42D, 43D, 44D)
        private Set<Integer> setValues2 = ImmutableSet.of(1, 2, 3)
    }

    private static class SetObjectEntity {
        @Persistable private Set<?> objectValues = ImmutableSet.of(new IntegerEntity(), new DoubleEntity())
    }

    private static class MapEntity {
        @Persistable private Map<?, ?> myMap = ImmutableMap.of(42, true, [42D], new BooleanEntity())
    }

    private enum TestEnum{
        FOURTY_TWO, FOURTY_THREE
    }

}
