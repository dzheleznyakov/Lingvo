package com.zheleznyakov.lingvo.persistence.xml.serializers

import groovy.xml.MarkupBuilder

trait CharXmlSerializer implements XmlSerializer<Character> {
    @Override
    void serialize(Character value, MarkupBuilder builder, String tag) {
        builder."$tag"(value)
    }
}