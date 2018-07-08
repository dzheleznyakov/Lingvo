package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

import com.zheleznyakov.lingvo.persistence.xml.deserializers.BaseXmlDeserializer
import groovy.util.slurpersupport.GPathResult

trait EnumXmlDeserializer<E extends Enum<E>> implements BaseXmlDeserializer<E> {
    @Override
    E deserialize(GPathResult node, Class<E> clazz, def deserializer) {
        if (!exists(node))
            return null
        verifyValuePresence(node)
        return Enum.valueOf(clazz, node.text())
    }

    String getValueType() {
        "enum"
    }
}