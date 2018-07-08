package com.zheleznyakov.lingvo.persistence.xml.serializers.collections

import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait BaseCollectionXmlSerializer<E extends Collection> implements XmlSerializer<E> {
    @Override
    void serialize(E values, MarkupBuilder builder, String tag, def attributes, def serializer) {
        updateAttributes(values, attributes)
        builder."$tag"(attributes) {
            values.each { value -> serializer.serialize(value, builder, "elem", ['type': value.getClass().typeName]) }
        }
    }

    abstract void updateAttributes(values, attributes)
}