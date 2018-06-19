package com.zheleznyakov.lingvo.persistence.xml.serializers.basic

import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait CollectionXmlSerializer implements XmlSerializer<Collection<?>> {
    @Override
    void serialize(Collection<?> values, MarkupBuilder builder, String tag) {
        builder."$tag" {
            values.each { value -> serialize(value, builder, value.getClass().simpleName) }
        }
    }
}