package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

import com.zheleznyakov.lingvo.persistence.xml.deserializers.XmlDeserializer
import com.zheleznyakov.lingvo.util.Util
import groovy.util.slurpersupport.GPathResult

trait BooleanXmlDeserializer implements XmlDeserializer<Boolean> {
    @Override
    Boolean deserialize(GPathResult node, Class<Boolean> clazz, def serializationContext) {
        def value = node.text()
        if (value == "true")
            return true
        else if (value == "false")
            return false
        else
            throw new IllegalArgumentException(Util.format("'{}'=[{}] is not a boolean value", node.name(), value))
    }
}