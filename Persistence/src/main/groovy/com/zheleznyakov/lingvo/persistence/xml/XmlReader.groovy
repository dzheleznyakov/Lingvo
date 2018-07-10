package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableMap
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.persistence.xml.deserializers.XmlDeserializer
import groovy.transform.PackageScope

@PackageScope
class XmlReader implements XmlPersistenceV1{
    private Deserializer deserializer

    XmlReader() {
        this(ImmutableMap.of())
    }

    XmlReader(Map<Class, Class<XmlDeserializer>> additionalDeserializers) {
        deserializer = [additionalDeserializers]
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
}
