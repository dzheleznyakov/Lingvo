package com.zheleznyakov.lingvo.persistence.xml.deserializers.maps

import com.zheleznyakov.lingvo.persistence.xml.deserializers.BaseXmlDeserializer
import groovy.util.slurpersupport.GPathResult

trait BaseMapXmlDeserializer<E extends Map> implements BaseXmlDeserializer<E> {
    @Override
    E deserialize(GPathResult node, Class<E> clazz, Object deserializer) {
        def exactClass = Class.forName(node.@type)
        def builder = builder(exactClass)
        for (int i = 0; i < node.entry.size(); i++) {
            def entry = node.entry[i]
            def keyClass = Class.forName(entry.key.@type)
            def key = deserializer.deserialize(entry.key, keyClass)
            def valueClass = Class.forName(entry.value.@type)
            def value = deserializer.deserialize(entry.value, valueClass)
            put(builder, key, value)
        }
        return build(builder)
    }

    abstract def builder(Class aClass)

    abstract void put(builder, key, value)

    abstract def build(builder)
}