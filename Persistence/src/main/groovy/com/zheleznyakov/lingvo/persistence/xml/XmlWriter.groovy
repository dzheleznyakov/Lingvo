package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.basic.*
import groovy.transform.PackageScope
import groovy.xml.MarkupBuilder

@PackageScope
class XmlWriter {
    private static final def DEFAULT_SERIALIZERS = [
            BooleanXmlSerializer,
            CharXmlSerializer,
            NumberXmlSerializer,
            EnumXmlSerializer,
            StringXmlSerializer,
            ObjectXmlSerializer,
            ArrayXmlSerializer,
            CollectionXmlSerializer,
            MapXmlSerializer
    ]

    private final MarkupBuilder builder
    private final def serializer

    private XmlWriter(Writer writer, Collection<XmlSerializer> additionalSerializers) {
        builder = new MarkupBuilder(writer)
        def traits = (DEFAULT_SERIALIZERS + additionalSerializers.toList()).toArray([] as Class<?>[])
        serializer = new Object().withTraits traits
    }

    static XmlWriter with(Writer writer) {
        return [writer, []]
    }

    static XmlWriter with(Writer writer, Collection<XmlSerializer<?>> additionalSerializers) {
        return [writer, additionalSerializers]
    }

    void write(LearningDictionary dictionary) {
        builder.root() {
            writePersistenceMetadata(builder)
            serializer.serialize(dictionary, builder, "dictionary")
        }
    }

    void write(Object entity) {
        serializer.serialize(entity, builder, entity.class.simpleName)
    }

    private void writePersistenceMetadata(builder) {
        builder.persistenceManager(version: 'v1', type: 'xml')
    }
}
