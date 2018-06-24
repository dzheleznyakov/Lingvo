package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

trait FloatXmlDeserializer implements NumberXmlDeserializer<Float> {
    @Override
    Float parse(String value) {
        return Float.parseFloat(value)
    }

    @Override
    String getValueType() {
        return "float"
    }
}