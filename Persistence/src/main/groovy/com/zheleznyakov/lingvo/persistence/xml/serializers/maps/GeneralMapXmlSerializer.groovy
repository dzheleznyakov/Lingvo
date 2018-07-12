package com.zheleznyakov.lingvo.persistence.xml.serializers.maps

trait GeneralMapXmlSerializer implements BaseMapXmlSerializer<Map> {
    @Override
    void updateAttributes(map, attributes) {
        attributes['type'] = map.getClass().canonicalName
    }
}