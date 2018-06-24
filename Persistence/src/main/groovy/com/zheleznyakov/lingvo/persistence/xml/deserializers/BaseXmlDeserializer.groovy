package com.zheleznyakov.lingvo.persistence.xml.deserializers

import com.zheleznyakov.lingvo.util.Util
import groovy.util.slurpersupport.GPathResult

trait BaseXmlDeserializer<E> implements XmlDeserializer<E> {
    void verifyNodeExistence(GPathResult node) {
        Util.validateArgument(node.size() > 0, "Node '{}' does not exist", node.name())
    }

    void verifyValuePresence(GPathResult node) {
        Util.validateArgument(!Util.isBlank(node.text()), "Node '{}' contains no {} value", node.name(), getValueType())
    }

    boolean nodeIsAbsent(GPathResult node) {
        return node.size() == 0
    }
}