package com.zheleznyakov.lingvo.persistence.xml.serializers

import groovy.xml.MarkupBuilder

trait EnumXmlSerializer implements XmlSerializer<Enum<?>> {
    @Override
    void serialize(Enum<?> value, MarkupBuilder builder, String tag) {
        builder."$tag"(value.name())
    }
}