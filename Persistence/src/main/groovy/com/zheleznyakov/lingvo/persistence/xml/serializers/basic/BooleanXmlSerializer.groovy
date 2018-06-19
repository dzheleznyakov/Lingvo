package com.zheleznyakov.lingvo.persistence.xml.serializers.basic

import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait BooleanXmlSerializer implements XmlSerializer<Boolean> {
    @Override
    void serialize(Boolean value, MarkupBuilder builder, String tag) {
        builder."$tag"(value)
    }
}
