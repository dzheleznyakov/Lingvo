package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.persistence.xml.deserializers.XmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.BooleanXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.ByteXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.CharXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.DoubleXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.FloatXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.IntXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.LongXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.ObjectXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.ShortXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.StringXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.collections.CollectionXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.collections.ImmutableSetXmlDeserializer
import groovy.util.slurpersupport.GPathResult

class XmlReader {
    public static final String VERSION = "v1"
    public static final String TYPE = "xml"

    private static final def DEFAULT_DESERIALIZERS = ImmutableMap.builder()
            .put(boolean,      BooleanXmlDeserializer)
            .put(Boolean,      BooleanXmlDeserializer)
            .put(char,         CharXmlDeserializer)
            .put(Character,    CharXmlDeserializer)
            .put(byte,         ByteXmlDeserializer)
            .put(Byte,         ByteXmlDeserializer)
            .put(short,        ShortXmlDeserializer)
            .put(Short,        ShortXmlDeserializer)
            .put(int,          IntXmlDeserializer)
            .put(Integer,      IntXmlDeserializer)
            .put(long,         LongXmlDeserializer)
            .put(Long,         LongXmlDeserializer)
            .put(float,        FloatXmlDeserializer)
            .put(Float,        FloatXmlDeserializer)
            .put(double,       DoubleXmlDeserializer)
            .put(Double,       DoubleXmlDeserializer)
            .put(String,       StringXmlDeserializer)
            .put(Object,       ObjectXmlDeserializer)
            .put(Collection,   CollectionXmlDeserializer)
            .put(ImmutableSet, ImmutableSetXmlDeserializer)
            .build()

    private Deserializer deserializer

    XmlReader() {
        this(ImmutableMap.of())
    }

    XmlReader(Map<Class<?>, Class<? extends XmlDeserializer<?>>> additionalDeserializers) {
        deserializer = new Deserializer(additionalDeserializers)
    }

    LearningDictionary read(File file) {
        if (!file.exists())
            throw new PersistenceException("File [" + file.absolutePath + "] is not found")
        def root = new XmlSlurper().parse(file)
        verifyMetadata(root)

        def inputStream = new BufferedInputStream(new FileInputStream(file))
        return read(inputStream, LearningDictionary)
    }

    def read(InputStream input, Class<?> clazz) {
        def root = new XmlSlurper().parse(input)
        return deserializer.deserialize(root, clazz)
    }

    private void verifyMetadata(def root) {
        def metadata = root.persistenceManager
        def version = metadata.@version
        if (version != VERSION)
            throw new PersistenceException("Wrong version: expected [$VERSION], but found [$version]")
        def type = metadata.@type
        if (type != TYPE)
            throw new PersistenceException("Wrong type: expected [$TYPE], but found [$type]")
    }

    private static trait DeserializerMatcher {
        def <E> XmlDeserializer<? extends E> getBestMatch(Class<E> clazz) {
            if (containsKey(clazz))
                return get(clazz)
            else if (Collection.class.isAssignableFrom(clazz))
                return get(Collection)
            else
                return get(Object)
        }
    }

    private static class Deserializer {
        private def deserializer

        Deserializer(def additionalDeserializers) {
            def desBuilder = ImmutableMap.builder()
            (DEFAULT_DESERIALIZERS + additionalDeserializers).each {entry ->
                def des = new Object().withTraits entry.value
                desBuilder.put(entry.key, des)
            }
            deserializer = desBuilder.build().withTraits DeserializerMatcher, Map
        }

        def deserialize(GPathResult node, Class<?> clazz) {
            def des = deserializer.getBestMatch(clazz)
            return des.deserialize(node, clazz, this)
        }
    }


}
