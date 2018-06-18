package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.persistence.Persistable
import com.zheleznyakov.lingvo.persistence.PersistenceRegistry
import com.zheleznyakov.lingvo.persistence.xml.serializers.ArrayXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.BooleanXmlSerializer

import com.zheleznyakov.lingvo.persistence.xml.serializers.CharXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.CollectionXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.EnumXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.MapXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.NumberXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.ObjectXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.StringXmlSerializer
import com.zheleznyakov.lingvo.util.Util
import groovy.transform.PackageScope
import groovy.xml.MarkupBuilder

import java.lang.reflect.Field

@PackageScope
class XmlWriter {
    private static def serializers = [
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

    private XmlWriter(Writer writer) {
        builder = new MarkupBuilder(writer)
        def traits = serializers.toArray([] as Class<?>[])
        serializer = new Object().withTraits traits
    }

    static XmlWriter with(Writer writer) {
        return [writer]
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
