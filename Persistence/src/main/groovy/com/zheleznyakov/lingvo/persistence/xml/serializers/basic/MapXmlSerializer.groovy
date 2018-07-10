package com.zheleznyakov.lingvo.persistence.xml.serializers.basic

import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait MapXmlSerializer implements XmlSerializer<Map<?, ?>> {
    @Override
    void serialize(Map<?, ?> map, MarkupBuilder builder, String tag, def attributes, def serializer) {
        builder."$tag"(attributes) {
            map.entrySet().each { en ->
                entry() {
                    serializer.serialize(en.key, builder, en.key.getClass().simpleName, [:])
                    serializer.serialize(en.value, builder, en.value.getClass().simpleName, [:])
                }
            }
        }
    }
}