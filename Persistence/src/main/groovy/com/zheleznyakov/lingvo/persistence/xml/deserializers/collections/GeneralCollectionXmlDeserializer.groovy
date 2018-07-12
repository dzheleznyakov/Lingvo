package com.zheleznyakov.lingvo.persistence.xml.deserializers.collections

trait GeneralCollectionXmlDeserializer implements BaseCollectionXmlDeserializer<Collection> {
    @Override
    def builder(clazz) {
        return clazz.newInstance()
    }

    @Override
    void add(builder, item) {
        builder.add(item)
    }

    @Override
    def build(builder) {
        return builder
    }
}