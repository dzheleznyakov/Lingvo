package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.basic.ArrayXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.basic.BooleanXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.basic.CharXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.basic.EnumXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.maps.ImmutableMapXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.maps.MapXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.basic.NumberXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.basic.ObjectXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.basic.StringXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.collections.CollectionXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.collections.ImmutableSetXmlSerializer
import groovy.transform.PackageScope
import groovy.xml.MarkupBuilder

@PackageScope
class Serializer {
    private static interface Array {}
    private static final def DEFAULT_SERIALIZERS = ImmutableMap.builder().
            put(boolean,      BooleanXmlSerializer).
            put(Boolean,      BooleanXmlSerializer).
            put(char,         CharXmlSerializer).
            put(Character,    CharXmlSerializer).
            put(Number,       NumberXmlSerializer).
            put(Enum,         EnumXmlSerializer).
            put(String,       StringXmlSerializer).
            put(Array,        ArrayXmlSerializer).
            put(ImmutableSet, ImmutableSetXmlSerializer).
            put(Collection,   CollectionXmlSerializer).
            put(ImmutableMap, ImmutableMapXmlSerializer).
            put(Map,          MapXmlSerializer).
            put(Object,       ObjectXmlSerializer).
            build()

    private final Map serializersByClass

    Serializer(def additionalSerializers) {
        def serBuilder = ImmutableMap.builder()
        (additionalSerializers + DEFAULT_SERIALIZERS).each {entry ->
            def ser = new Object().withTraits entry.value
            serBuilder.put(entry.key, ser)
        }
        serializersByClass = serBuilder.build()
    }

    def serialize(def entity, MarkupBuilder builder, String tag, def attributes) {
        def ser = getBestMatch(entity)
        ser.serialize(entity, builder, tag, attributes, this)
    }

    private  <E> XmlSerializer<? extends E> getBestMatch(entity) {
        Class<?> clazz = entity.getClass()
        if (containsKey(clazz))
            return get(clazz)
        else if (Number.class.isAssignableFrom(clazz) || clazz.isPrimitive())
            return get(Number)
        else if (clazz.isArray())
            return get(Array)
        else
            return getTheFirstAssignableFrom(entity)
    }

    private def getTheFirstAssignableFrom(entity) {
        Class entityClass = entity.getClass()
        for (Class clazz : keySet())
            if (clazz.isAssignableFrom(entityClass))
                return get(clazz)
    }

    private Set keySet() {
        return serializersByClass.keySet()
    }

    private boolean containsKey(clazz) {
        return serializersByClass.containsKey(clazz)
    }

    private def get(clazz) {
        serializersByClass.get(clazz)
    }
}
