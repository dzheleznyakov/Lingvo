package com.zheleznyakov.lingvo.persistence.xml.util

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.persistence.Persistable

class TestClasses {
    static class BooleanEntity {
        @Persistable private boolean booleanValue = true
        private boolean booleanValue2 = false
    }

    static class ShortEntity {
        @Persistable private short shortValue = 42 as short
        private short shortValue2 = (Math.random() * 100) as short
    }

    static class CharEntity {
        @Persistable private char charValue = 42 as char
        private char charValue2 = (Math.random() * 100) as char
    }

    static class ByteEntity {
        @Persistable private byte byteValue = 42 as byte
        private byte byteValue2 = (Math.random() * 100) as byte
    }

    static class IntegerEntity {
        @Persistable private int intValue = 42
        private int intValue2 = (Math.random() * 100) as int
    }

    static class LongEntity {
        @Persistable private long longValue = 42L
        private long longValue2 = Math.random() * 100L;
    }

    static class FloatEntity {
        @Persistable private float floatValue = 42F
        private float floatValue2 = (Math.random() * 100) as float
    }

    static class DoubleEntity {
        @Persistable private double doubleValue = 42D
        private double doubleValue2 = Math.random() * 100
    }

    static class EnumEntity {
        @Persistable private TestEnum enumValue = TestEnum.FORTY_TWO
        private TestEnum enumValue2 = TestEnum.FORTY_THREE

    }

    static class StringEntity {
        @Persistable private String stringValue = "testValue"
        private String stringValue2 = "another test value"
    }

    static class BooleanArrayEntity {
        @Persistable private boolean[] arrayValues = [true, true, false].toArray()
        private double[] arrayValues2 = [45D, 46D, 47D].toArray()
    }

    static class CharArrayEntity {
        @Persistable private char[] arrayValues = ['a', 'b', 'c'].toArray()
        private double[] arrayValues2 = [45D, 46D, 47D].toArray()
    }

    static class ByteArrayEntity {
        @Persistable private byte[] arrayValues = [42 as byte, 43 as byte, 44 as byte].toArray()
        private double[] arrayValues2 = [45D, 46D, 47D].toArray()
    }

    static class ShortArrayEntity {
        @Persistable private short[] arrayValues = [42 as short, 43 as short, 44 as short].toArray()
        private double[] arrayValues2 = [45D, 46D, 47D].toArray()
    }

    static class IntArrayEntity {
        @Persistable private int[] arrayValues = [42, 43, 44].toArray()
        private double[] arrayValues2 = [45D, 46D, 47D].toArray()
    }

    static class LongArrayEntity {
        @Persistable private long[] arrayValues = [42L, 43L, 44L].toArray()
        private double[] arrayValues2 = [45D, 46D, 47D].toArray()
    }

    static class FloatArrayEntity {
        @Persistable private float[] arrayValues = [42 as float, 43 as float, 44 as float].toArray()
        private double[] arrayValues2 = [45D, 46D, 47D].toArray()
    }

    static class DoubleArrayEntity {
        @Persistable private double[] arrayValues = [42D, 43D, 44D].toArray()
        private double[] doubleValues2 = [45D, 46D, 47D].toArray()
    }

    static class ObjectArrayEntity<E> {
        @Persistable private E[] arrayValues
        private double[] doubleValues2 = [45D, 46D, 47D].toArray()

        ObjectArrayEntity(E... values) {
            arrayValues = values
        }
    }

    static class ListEntity {
        @Persistable private List<Integer> listValues = ImmutableList.of(42, 43, 44)
        private List<Double> listValues2 = ImmutableList.of(45D, 46D, 47D)
    }

    static class SetEntity {
        @Persistable private Set<Double> setValues = ImmutableSet.of(42D, 43D, 44D)
        private Set<Integer> setValues2 = ImmutableSet.of(1, 2, 3)
    }

    static class SetObjectEntity {
        @Persistable private Set<?> objectValues = ImmutableSet.of(new IntegerEntity(), new DoubleEntity())
    }

    static class MapEntity {
        @Persistable private Map<?, ?> myMap = ImmutableMap.of(42, true, [42D], new BooleanEntity())
    }

    enum TestEnum{
        FORTY_TWO, FORTY_THREE
    }
}
