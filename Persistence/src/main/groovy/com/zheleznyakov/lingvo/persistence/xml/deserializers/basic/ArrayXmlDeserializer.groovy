package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

import com.zheleznyakov.lingvo.persistence.xml.deserializers.BaseXmlDeserializer
import groovy.util.slurpersupport.GPathResult

import java.lang.reflect.Array

class ArrayXmlDeserializer<E> implements BaseXmlDeserializer<E> {
    @Override
    E deserialize(GPathResult node, Class<E> clazz, def deserializer) {
        def xmlArray = getXmlArray(node)
        E array = getArray(xmlArray.size(), clazz.componentType)
        fillArray(array, xmlArray, deserializer)
        return array
    }

    private def getXmlArray(GPathResult node) {
        def iterator = node.children().iterator()
        def childName = iterator.hasNext() ? iterator.next().name() : 'notExistingChildrenTagName'
        return node."$childName"
    }

    private E getArray(int size, Class componentType) {
        switch (componentType) {
            case boolean:
                return new boolean[size]
            case char:
                return new char[size]
            case byte:
                return new byte[size]
            case short:
                return new short[size]
            case int:
                return new int[size]
            case long:
                return new long[size]
            case float:
                return new float[size]
            case double:
                return new double[size]
            default:
                return Array.newInstance(componentType, size)
        }
    }

    private void fillArray(def array, GPathResult xmlArray, def deserializer) {
        for (int i = 0; i < xmlArray.size(); i++)
            array[i] = deserializer.deserialize(xmlArray[i], array.class.componentType)
    }

    static <E> ArrayXmlDeserializer<E> get(Class<E> cl) {
        return new ArrayXmlDeserializer<>()
    }
}
