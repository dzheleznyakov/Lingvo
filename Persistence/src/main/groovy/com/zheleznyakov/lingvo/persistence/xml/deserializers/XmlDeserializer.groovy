package com.zheleznyakov.lingvo.persistence.xml.deserializers

import groovy.util.slurpersupport.GPathResult

interface XmlDeserializer<E> {
    E deserialize(GPathResult node, Class<E> clazz, def serializationContext)
}