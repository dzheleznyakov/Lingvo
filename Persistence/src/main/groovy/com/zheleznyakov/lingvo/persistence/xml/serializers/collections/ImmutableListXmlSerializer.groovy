package com.zheleznyakov.lingvo.persistence.xml.serializers.collections

import com.google.common.collect.ImmutableList

trait ImmutableListXmlSerializer implements BaseCollectionXmlSerializer<ImmutableList> {
    @Override
    void updateAttributes(values, attributes) {
        attributes['type'] = ImmutableList.class.typeName
    }
}