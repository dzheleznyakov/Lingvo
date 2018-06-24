package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

trait ByteXmlDeserializer implements NumberXmlDeserializer<Byte> {
    @Override
    Byte parse(String value) {
        return Byte.parseByte(value)
    }

    @Override
    String getValueType() {
        return "byte"
    }
}