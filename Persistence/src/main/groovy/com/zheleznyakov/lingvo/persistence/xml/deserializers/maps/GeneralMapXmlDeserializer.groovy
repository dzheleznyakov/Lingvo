package com.zheleznyakov.lingvo.persistence.xml.deserializers.maps

trait GeneralMapXmlDeserializer extends BaseMapXmlDeserializer<Map> {
    @Override
    def builder(Class clazz) {
        return clazz.newInstance()
    }

    @Override
    void put(builder, key, value) {
        builder.put(key, value)
    }

    @Override
    def build(builder) {
        return builder
    }
}