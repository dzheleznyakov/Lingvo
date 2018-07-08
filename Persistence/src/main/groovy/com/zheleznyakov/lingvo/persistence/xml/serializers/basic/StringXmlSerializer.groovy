package com.zheleznyakov.lingvo.persistence.xml.serializers.basic

import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait StringXmlSerializer implements XmlSerializer<String> {
    @Override
    void serialize(String value, MarkupBuilder builder, String tag, def attributes, def serializer) {
        builder."$tag"(attributes, value)
    }
}