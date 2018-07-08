package com.zheleznyakov.lingvo.persistence.xml.deserializers.collections

import com.zheleznyakov.lingvo.persistence.xml.deserializers.BaseXmlDeserializer
import groovy.util.slurpersupport.GPathResult

trait BaseCollectionXmlDeserializer<E extends Collection> implements BaseXmlDeserializer<E> {
    @Override
    E deserialize(GPathResult node, Class<E> clazz, def deserializer) {

        if (!exists(node))
            return null

        def exactClass = Class.forName(node.@type)
        def builder = builder(exactClass)
        for (int i = 0; i < node.elem.size(); i++) {
            def elem = node.elem[i]
            def elemType = Class.forName(elem.@type)
            def value = deserializer.deserialize(elem, elemType)
            add(builder, value)
        }
        return build(builder)
    }

    abstract def builder(clazz)

    abstract void add(builder, item)

    abstract def build(builder)
}