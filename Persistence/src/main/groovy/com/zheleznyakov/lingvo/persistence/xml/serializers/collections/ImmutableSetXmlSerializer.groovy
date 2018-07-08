package com.zheleznyakov.lingvo.persistence.xml.serializers.collections

import com.google.common.collect.ImmutableSet

trait ImmutableSetXmlSerializer implements BaseCollectionXmlSerializer<ImmutableSet> {
    @Override
    void updateAttributes(values, attributes) {
        attributes['type'] = ImmutableSet.class.typeName
    }
}