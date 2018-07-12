package com.zheleznyakov.lingvo.persistence.xml.deserializers.maps

import com.google.common.collect.ImmutableMap

trait ImmutableMapXmlDeserializer extends BaseMapXmlDeserializer<ImmutableMap> {
    @Override
    def builder(Class aClass) {
        return ImmutableMap.builder()
    }

    @Override
    void put(builder, key, value) {
        builder.put key, value
    }

    @Override
    def build(builder) {
        return builder.build()
    }
}