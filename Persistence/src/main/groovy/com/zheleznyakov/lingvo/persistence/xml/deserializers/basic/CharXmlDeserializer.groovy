package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

import com.zheleznyakov.lingvo.persistence.xml.deserializers.XmlDeserializer
import com.zheleznyakov.lingvo.util.Util
import groovy.util.slurpersupport.GPathResult

trait CharXmlDeserializer implements XmlDeserializer<Character> {
    @Override
    Character deserialize(GPathResult node, Class<Character> clazz, Object serializationContext) {
        def value = node.text()
        if (value.length() == 0)
            throw new IllegalArgumentException(Util.format("Node '{}' contains no character", node.name()))
        if (value.length() > 1)
            throw new IllegalArgumentException(Util.format("'{}'=[{}] is not a character", node.name(), value))
        return value.charAt(0)
    }
}
