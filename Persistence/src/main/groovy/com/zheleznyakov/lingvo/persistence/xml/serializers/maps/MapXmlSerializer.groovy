package com.zheleznyakov.lingvo.persistence.xml.serializers.maps

trait MapXmlSerializer implements BaseMapXmlSerializer<Map> {
    @Override
    void updateAttributes(map, attributes) {
        attributes['type'] = map.getClass().canonicalName
    }
}