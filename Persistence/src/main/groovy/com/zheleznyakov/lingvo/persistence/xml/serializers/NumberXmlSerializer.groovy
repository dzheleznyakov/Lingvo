package com.zheleznyakov.lingvo.persistence.xml.serializers

import groovy.xml.MarkupBuilder

trait NumberXmlSerializer implements XmlSerializer<Number> {
    @Override
    void serialize(Number number, MarkupBuilder builder, String tag) {
        builder."$tag"(number)
    }
}