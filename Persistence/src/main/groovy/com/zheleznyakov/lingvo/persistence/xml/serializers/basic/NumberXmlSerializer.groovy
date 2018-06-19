package com.zheleznyakov.lingvo.persistence.xml.serializers.basic

import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait NumberXmlSerializer implements XmlSerializer<Number> {
    @Override
    void serialize(Number number, MarkupBuilder builder, String tag) {
        builder."$tag"(number)
    }
}