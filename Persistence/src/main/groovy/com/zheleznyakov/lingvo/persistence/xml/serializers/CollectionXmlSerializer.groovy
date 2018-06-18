package com.zheleznyakov.lingvo.persistence.xml.serializers

import groovy.xml.MarkupBuilder

trait CollectionXmlSerializer implements XmlSerializer<Collection<?>> {
    @Override
    void serialize(Collection<?> values, MarkupBuilder builder, String tag) {
        builder."$tag" {
            values.each { value -> serialize(value, builder, value.getClass().simpleName) }
        }
    }
}