package com.zheleznyakov.lingvo.persistence.xml.util

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.persistence.Persistable

class BooleanEntity {
    @Persistable private boolean booleanValue = true
    private boolean booleanValue2 = false
    boolean getBooleanValue() {
        return booleanValue
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        BooleanEntity that = (BooleanEntity) o

        if (booleanValue != that.booleanValue) return false

        return true
    }

    int hashCode() {
        return (booleanValue ? 1 : 0)
    }
}

class ShortEntity {
    @Persistable private short shortValue = 42 as short
    private short shortValue2 = (Math.random() * 100) as short
    short getShortValue() {
        return shortValue
    }
}

class CharEntity {
    @Persistable private char charValue = 42 as char
    private char charValue2 = (Math.random() * 100) as char
    char getCharValue() {
        return charValue
    }
}

class ByteEntity {
    @Persistable private byte byteValue = 42 as byte
    private byte byteValue2 = (Math.random() * 100) as byte
    byte getByteValue() {
        return byteValue
    }
}

class IntegerEntity {
    @Persistable private int integerValue = 42
    private int intValue2 = (Math.random() * 100) as int

    int getIntValue() {
        return integerValue
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        IntegerEntity that = (IntegerEntity) o

        if (integerValue != that.intValue) return false

        return true
    }

    int hashCode() {
        return integerValue
    }
}

class LongEntity {
    @Persistable private long longValue = 42L
    private long longValue2 = Math.random() * 100L;
    long getLongValue() {
        return longValue
    }
}

class FloatEntity {
    @Persistable private float floatValue = 42F
    private float floatValue2 = (Math.random() * 100) as float
    float getFloatValue() {
        return floatValue
    }
}

class DoubleEntity {
    @Persistable private double doubleValue = 42D
    private double doubleValue2 = Math.random() * 100
    double getDoubleValue() {
        return doubleValue
    }
    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        DoubleEntity that = (DoubleEntity) o

        if (Double.compare(that.doubleValue, doubleValue) != 0) return false

        return true
    }

    int hashCode() {
        long temp = doubleValue != +0.0d ? Double.doubleToLongBits(doubleValue) : 0L
        return (int) (temp ^ (temp >>> 32))
    }
}

class EnumEntity {
    @Persistable private TestEnum enumValue = TestEnum.FORTY_TWO
    private TestEnum enumValue2 = TestEnum.FORTY_THREE
    TestEnum getEnumValue() {
        return enumValue
    }
}

class StringEntity {
    @Persistable private String stringValue = "testValue"
    private String stringValue2 = "another test value"
    String getStringValue() {
        return stringValue
    }
}

class BooleanArrayEntity {
    @Persistable private boolean[] arrayValues = [true, true, false].toArray()
    private double[] arrayValues2 = [45D, 46D, 47D].toArray()
}

class CharacterArrayEntity {
    @Persistable private char[] arrayValues = ['a', 'b', 'c'].toArray()
    private double[] arrayValues2 = [45D, 46D, 47D].toArray()
}

class ByteArrayEntity {
    @Persistable private byte[] arrayValues = [42 as byte, 43 as byte, 44 as byte].toArray()
    private double[] arrayValues2 = [45D, 46D, 47D].toArray()
}

class ShortArrayEntity {
    @Persistable private short[] arrayValues = [42 as short, 43 as short, 44 as short].toArray()
    private double[] arrayValues2 = [45D, 46D, 47D].toArray()
}

class IntegerArrayEntity {
    @Persistable private int[] arrayValues = [42, 43, 44].toArray()
    private double[] arrayValues2 = [45D, 46D, 47D].toArray()
}

class LongArrayEntity {
    @Persistable private long[] arrayValues = [42L, 43L, 44L].toArray()
    private double[] arrayValues2 = [45D, 46D, 47D].toArray()
}

class FloatArrayEntity {
    @Persistable private float[] arrayValues = [42 as float, 43 as float, 44 as float].toArray()
    private double[] arrayValues2 = [45D, 46D, 47D].toArray()
}

class DoubleArrayEntity {
    @Persistable private double[] arrayValues = [42D, 43D, 44D].toArray()
    private double[] doubleValues2 = [45D, 46D, 47D].toArray()
}

class ObjectArrayEntity<E> {
    @Persistable private E[] arrayValues
    private double[] doubleValues2 = [45D, 46D, 47D].toArray()

    ObjectArrayEntity(E... values) {
        arrayValues = values
    }
}

class ListEntity {
    @Persistable private List<Integer> listValues = [42, 43, 44]
    private List<Double> listValues2 = ImmutableList.of(45D, 46D, 47D)
    List<Integer> getListValues() {
        return listValues
    }
}

class SetEntity {
    @Persistable private Set<Double> setValues = [42D, 43D, 44D].toSet()
    private Set<Integer> setValues2 = ImmutableSet.of(1, 2, 3)
    Set<Double> getSetValues() {
        return setValues
    }
}

class SetObjectEntity {
    @Persistable private Set<?> setObjectValues = ImmutableSet.of(new IntegerEntity(), new DoubleEntity())
    Set<?> getSetObjectValues() {
        return setObjectValues
    }
}

class MapEntity {
    @Persistable private Map myMap = ImmutableMap.of(42, true, [42D], new BooleanEntity())

    Map getMyMap() {
        return myMap
    }

    MapEntity setMyMap( myMap) {
        this.myMap = myMap
        return this
    }
}

enum TestEnum{
    FORTY_TWO, FORTY_THREE
}
