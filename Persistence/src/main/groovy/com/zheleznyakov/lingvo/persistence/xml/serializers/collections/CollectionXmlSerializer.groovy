package com.zheleznyakov.lingvo.persistence.xml.serializers.collections

trait CollectionXmlSerializer implements BaseCollectionXmlSerializer<Collection> {
    @Override
    void updateAttributes(values, attributes) {
        attributes['type'] = values.getClass().typeName
    }
}