package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

import com.zheleznyakov.lingvo.persistence.xml.deserializers.BaseXmlDeserializer
import groovy.util.slurpersupport.GPathResult

trait StringXmlDeserializer implements BaseXmlDeserializer<String> {
    @Override
    String deserialize(GPathResult node, Class<String> clazz, Object deserializer) {
        if (nodeIsAbsent(node))
            return null
        return node.text()
    }
}