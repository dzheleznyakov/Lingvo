package com.zheleznyakov.lingvo.persistence.xml.deserializers

import com.zheleznyakov.lingvo.util.Util
import groovy.util.slurpersupport.GPathResult

trait NumberXmlDeserializer<E extends Number> implements BaseXmlDeserializer<E> {
    @Override
    E deserialize(GPathResult node, Class<E> clazz, def deserializer) {
        verifyNodeExistence(node)
        verifyValuePresence(node)

        def value = node.text()
        try {
            return parse(value)
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Util.format("'{}'=[{}] is not a {} value", node.name(), value, getValueType()))
        }
    }

    abstract E parse(String value)

    abstract String getValueType()
}