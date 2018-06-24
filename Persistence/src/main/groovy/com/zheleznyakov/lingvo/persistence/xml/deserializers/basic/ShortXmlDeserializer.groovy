package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

trait ShortXmlDeserializer implements NumberXmlDeserializer<Short> {
    @Override
    Short parse(String value) {
        return Short.parseShort(value)
    }

    @Override
    String getValueType() {
        return "short"
    }
}