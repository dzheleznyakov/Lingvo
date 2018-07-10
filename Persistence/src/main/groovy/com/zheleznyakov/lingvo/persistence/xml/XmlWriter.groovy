package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.transform.PackageScope
import groovy.xml.MarkupBuilder

@PackageScope
class XmlWriter implements XmlPersistenceV1 {
    private final MarkupBuilder builder
    private final Serializer serializer

    private XmlWriter(Writer writer, Map<Class, XmlSerializer> additionalSerializers) {
        builder = [writer]
        serializer = [additionalSerializers]
    }

    static XmlWriter with(Writer writer) {
        return [writer, [:]]
    }

    static XmlWriter with(Writer writer, Map<Class, XmlSerializer> additionalSerializers) {
        return [writer, additionalSerializers]
    }

    void write(LearningDictionary dictionary) {
        builder.root() {
            writePersistenceMetadata(builder)
            serializer.serialize(dictionary, builder, "dictionary", [:])
        }
    }

    void write(Object entity) {
        serializer.serialize(entity, builder, entity.class.simpleName, [:])
    }

    private void writePersistenceMetadata(builder) {
        builder.persistenceManager(version: VERSION, type: TYPE)
    }
}
