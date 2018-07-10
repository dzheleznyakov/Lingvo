package com.zheleznyakov.lingvo.persistence.xml.serializers.maps

import com.google.common.collect.ImmutableMap

trait ImmutableMapXmlSerializer implements BaseMapXmlSerializer<ImmutableMap> {
    @Override
    void updateAttributes(map, attributes) {
        attributes['type'] = ImmutableMap.class.canonicalName
    }
}