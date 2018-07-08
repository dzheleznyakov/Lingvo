package com.zheleznyakov.lingvo.persistence.xml.serializers.basic

import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait CharXmlSerializer implements XmlSerializer<Character> {
    @Override
    void serialize(Character value, MarkupBuilder builder, String tag, def attributes, def serializer) {
        builder."$tag"(attributes, value)
    }
}