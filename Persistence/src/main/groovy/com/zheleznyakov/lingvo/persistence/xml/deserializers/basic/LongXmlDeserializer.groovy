package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

import com.zheleznyakov.lingvo.persistence.xml.deserializers.NumberXmlDeserializer

trait LongXmlDeserializer implements NumberXmlDeserializer<Long> {
    @Override
    Long parse(String value) {
        return Long.parseLong(value)
    }

    @Override
    String getValueType() {
        return "long"
    }
}