package com.zheleznyakov.lingvo.persistence.xml.serializers.maps

import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait BaseMapXmlSerializer<E extends Map> implements XmlSerializer<E> {
    @Override
    void serialize(Map map, MarkupBuilder builder, String tag, def attributes, def serializer) {
        updateAttributes(map, attributes)
        builder."$tag"(attributes) {
            map.entrySet().each { en ->
                entry() {
                    serializer.serialize(en.key, builder, en.key.getClass().simpleName, [:])
                    serializer.serialize(en.value, builder, en.value.getClass().simpleName, [:])
                }
            }
        }
    }

    abstract void updateAttributes(map, attributes)
}