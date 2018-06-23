package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

import com.zheleznyakov.lingvo.persistence.xml.deserializers.XmlDeserializer
import com.zheleznyakov.lingvo.util.Util
import groovy.util.slurpersupport.GPathResult

trait ShortXmlDeserializer implements XmlDeserializer<Short> {
    @Override
    Short deserialize(GPathResult node, Class<Short> clazz, Object serializationContext) {
        def value = node.text()
        if (value.length() == 0)
            throw new IllegalArgumentException(Util.format("Node '{}' contains no short value", node.name()))
        try {
            return Short.parseShort(value)
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Util.format("'{}'=[{}] is not a short value", node.name(), value))
        }
    }
}