package com.zheleznyakov.lingvo.persistence.xml.serializers

import groovy.xml.MarkupBuilder

trait MapXmlSerializer implements XmlSerializer<Map<?, ?>> {
    @Override
    void serialize(Map<?, ?> map, MarkupBuilder builder, String tag) {
        builder."$tag" {
            map.entrySet().each { en ->
                entry() {
                    serialize(en.key, builder, en.key.getClass().simpleName)
                    serialize(en.value, builder, en.value.getClass().simpleName)
                }
            }
        }
    }
}