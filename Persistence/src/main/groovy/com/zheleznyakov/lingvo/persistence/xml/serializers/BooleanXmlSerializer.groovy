package com.zheleznyakov.lingvo.persistence.xml.serializers

import groovy.xml.MarkupBuilder

trait BooleanXmlSerializer implements XmlSerializer<Boolean> {
    @Override
    void serialize(Boolean value, MarkupBuilder builder, String tag) {
        builder."$tag"(value)
    }
}
