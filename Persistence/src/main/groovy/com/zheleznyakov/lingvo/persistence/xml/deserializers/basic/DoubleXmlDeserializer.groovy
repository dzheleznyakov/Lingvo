package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

import com.zheleznyakov.lingvo.persistence.xml.deserializers.NumberXmlDeserializer

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