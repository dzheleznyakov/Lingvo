package com.zheleznyakov.lingvo.persistence.xml.serializers

import groovy.xml.MarkupBuilder

interface XmlSerializer<E> {
    void serialize(E value, MarkupBuilder builder, String tag)
}