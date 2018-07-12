package com.zheleznyakov.lingvo.persistence.xml.serializers.collections

trait GeneralCollectionXmlSerializer implements BaseCollectionXmlSerializer<Collection> {
    @Override
    void updateAttributes(values, attributes) {
        attributes['type'] = values.getClass().typeName
    }
}