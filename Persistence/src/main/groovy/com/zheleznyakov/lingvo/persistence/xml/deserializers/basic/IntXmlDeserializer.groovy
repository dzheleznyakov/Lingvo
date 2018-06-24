package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

trait IntXmlDeserializer implements NumberXmlDeserializer<Integer> {
    @Override
    Integer parse(String value) {
        return Integer.parseInt(value)
    }

    @Override
    String getValueType() {
        return "integer"
    }
}