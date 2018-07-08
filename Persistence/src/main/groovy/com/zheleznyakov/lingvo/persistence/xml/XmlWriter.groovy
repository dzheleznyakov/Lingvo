package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.basic.*
import com.zheleznyakov.lingvo.persistence.xml.serializers.collections.CollectionXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.collections.ImmutableSetXmlSerializer
import groovy.transform.PackageScope
import groovy.xml.MarkupBuilder

import java.lang.reflect.Array

@PackageScope
class XmlWriter {
//    private static final def DEFAULT_SERIALIZERS = [
//            BooleanXmlSerializer,
//            CharXmlSerializer,
//            NumberXmlSerializer,
//            EnumXmlSerializer,
//            StringXmlSerializer,
//            ObjectXmlSerializer,
//            ArrayXmlSerializer,
//            ImmutableSetXmlSerializer,
//            CollectionXmlSerializer,
//            MapXmlSerializer
//    ]

    private static final def DEFAULT_SERIALIZERS = ImmutableMap.builder()
            .put(boolean,      BooleanXmlSerializer)
            .put(Boolean,      BooleanXmlSerializer)
            .put(char,         CharXmlSerializer)
            .put(Character,    CharXmlSerializer)
            .put(Number,       NumberXmlSerializer)
            .put(Enum,         EnumXmlSerializer)
            .put(String,       StringXmlSerializer)
            .put(Object,       ObjectXmlSerializer)
            .put(Array,        ArrayXmlSerializer)
            .put(Collection,   CollectionXmlSerializer)
            .put(ImmutableSet, ImmutableSetXmlSerializer)
            .put(Map,          MapXmlSerializer)
            .build()



    private final MarkupBuilder builder
    private final Serializer serializer

    private XmlWriter(Writer writer, Collection<XmlSerializer> additionalSerializers) {
        builder = new MarkupBuilder(writer)
//        def traits = (DEFAULT_SERIALIZERS + additionalSerializers.toList()).toArray([] as Class<?>[])
//        serializer = new Object().withTraits traits
        serializer = new Serializer(additionalSerializers)
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
            serializer.serialize(dictionary, builder, "dictionary", [:])
        }
    }

    void write(Object entity) {
        serializer.serialize(entity, builder, entity.class.simpleName, [:])
    }

    private void writePersistenceMetadata(builder) {
        builder.persistenceManager(version: 'v1', type: 'xml')
    }

    private static trait SerializerMatcher {
        def <E> XmlSerializer<? extends E> getBestMatch(entity) {
            Class<?> clazz = entity.getClass()
            if (containsKey(clazz))
                return get(clazz)
            else if (Number.class.isAssignableFrom(clazz))
                return get(Number)
            else if (clazz.isPrimitive())
                return get(Number)
            else if (Enum.isAssignableFrom(clazz))
                return get(Enum)
            else if (Collection.class.isAssignableFrom(clazz))
                return get(Collection)
            else
                return get(Object)
        }
    }

    private static class Serializer {
        private def serializer

        Serializer(def additionalSerializers) {
            def serBuilder = ImmutableMap.builder()
            (DEFAULT_SERIALIZERS + additionalSerializers).each {entry ->
                def ser = new Object().withTraits entry.value
                serBuilder.put(entry.key, ser)
            }
            serializer = serBuilder.build().withTraits SerializerMatcher, Map
        }

        def serialize(def entity, MarkupBuilder builder, String tag, def attributes) {
            def ser = serializer.getBestMatch(entity)
            ser.serialize(entity, builder, tag, attributes, this)
        }
    }
}
