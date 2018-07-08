package com.zheleznyakov.lingvo.persistence.xml.deserializers.collections

import com.google.common.collect.ImmutableSet

trait ImmutableSetXmlDeserializer extends BaseCollectionXmlDeserializer<ImmutableSet> {
    @Override
    def builder(clazz) {
        return ImmutableSet.builder()
    }

    @Override
    void add(builder, item) {
        builder.add(item)
    }

    @Override
    def build(builder) {
        return builder.build()
    }
}