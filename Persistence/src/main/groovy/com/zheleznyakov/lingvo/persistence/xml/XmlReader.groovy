package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableMap
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.BooleanXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.ByteXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.CharXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.DoubleXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.FloatXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.IntXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.LongXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.ObjectXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.deserializers.basic.ShortXmlDeserializer

class XmlReader {
    public static final String VERSION = "v1"
    public static final String TYPE = "xml"

    private static final def DEFAULT_DESERIALIZERS = ImmutableMap.builder()
            .put(boolean.class,   BooleanXmlDeserializer)
            .put(Boolean.class,   BooleanXmlDeserializer)
            .put(char.class,      CharXmlDeserializer)
            .put(Character.class, CharXmlDeserializer)
            .put(byte.class,      ByteXmlDeserializer)
            .put(Byte.class,      ByteXmlDeserializer)
            .put(short.class,     ShortXmlDeserializer)
            .put(Short.class,     ShortXmlDeserializer)
            .put(int.class,       IntXmlDeserializer)
            .put(Integer.class,   IntXmlDeserializer)
            .put(long.class,      LongXmlDeserializer)
            .put(Long.class,      LongXmlDeserializer)
            .put(float.class,     FloatXmlDeserializer)
            .put(Float.class,     FloatXmlDeserializer)
            .put(double.class,    DoubleXmlDeserializer)
            .put(Double.class,    DoubleXmlDeserializer)
            .put(Object.class,    ObjectXmlDeserializer)
            .build()

    private def deserializer

    private File file

    XmlReader() {
        def deserializersBuilder = ImmutableMap.builder()
        DEFAULT_DESERIALIZERS.each {entry ->
            def des = new Object().withTraits entry.value
            deserializersBuilder.put(entry.key, des)
        }
        deserializer = deserializersBuilder.build()
    }

    LearningDictionary read() {
        if (!file.exists())
            throw new PersistenceException("File [" + file.absolutePath + "] is not found")
        def root = new XmlSlurper().parse(file)
        verifyMetadata(root)

        return null
    }

    def read(InputStream input, Class<?> clazz) {
        def root = new XmlSlurper().parse(input)
        def des = deserializer.getOrDefault(clazz, deserializer.get(Object.class))
        return des.deserialize(root, clazz, deserializer)
//        println root.childNodes().next().getClass()
//        return clazz.newInstance()
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
}
