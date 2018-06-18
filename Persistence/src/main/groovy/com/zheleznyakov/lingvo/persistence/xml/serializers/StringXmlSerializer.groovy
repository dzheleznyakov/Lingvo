package com.zheleznyakov.lingvo.persistence.xml.serializers

import groovy.xml.MarkupBuilder

trait StringXmlSerializer implements XmlSerializer<String> {
    @Override
    void serialize(String value, MarkupBuilder builder, String tag) {
        builder."$tag"(value)
    }
}