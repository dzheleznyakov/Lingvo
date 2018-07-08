package com.zheleznyakov.lingvo.persistence.xml.serializers.basic

import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait EnumXmlSerializer implements XmlSerializer<Enum<?>> {
    @Override
    void serialize(Enum<?> value, MarkupBuilder builder, String tag, def attributes, def serializer) {
        builder."$tag"(attributes, value.name())
    }
}