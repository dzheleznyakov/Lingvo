package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

import com.zheleznyakov.lingvo.persistence.xml.deserializers.XmlDeserializer
import com.zheleznyakov.lingvo.util.Util
import groovy.util.slurpersupport.GPathResult

trait ByteXmlDeserializer implements XmlDeserializer<Byte> {
    @Override
    Byte deserialize(GPathResult node, Class<Byte> clazz, Object serializationContext) {
        def value = node.text()
        if (value.length() == 0)
            throw new IllegalArgumentException(Util.format("Node '{}' contains no byte value", node.name()))
        try {
            return Byte.parseByte(value)
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Util.format("'{}'=[{}] is not a byte value", node.name(), value))
        }
    }
}