package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

trait DoubleXmlDeserializer implements NumberXmlDeserializer<Double> {
    @Override
    Double parse(String value) {
        return Double.parseDouble(value)
    }

    @Override
    String getValueType() {
        return "double"
    }
}