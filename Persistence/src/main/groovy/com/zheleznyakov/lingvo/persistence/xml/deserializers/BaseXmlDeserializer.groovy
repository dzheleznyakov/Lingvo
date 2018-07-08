package com.zheleznyakov.lingvo.persistence.xml.deserializers

import com.zheleznyakov.lingvo.util.Util
import groovy.util.slurpersupport.GPathResult

trait BaseXmlDeserializer<E> implements XmlDeserializer<E> {
    void verifyNodeExistence(GPathResult node) {
        Util.validateArgument(exists(node), "Node '{}' does not exist", node.name())
    }

    void verifyValuePresence(GPathResult node) {
        Util.validateArgument(!Util.isBlank(node.text()), "Node '{}' contains no {} value", node.name(), getValueType())
    }

    boolean exists(GPathResult node) {
        return node.size() > 0
    }
}