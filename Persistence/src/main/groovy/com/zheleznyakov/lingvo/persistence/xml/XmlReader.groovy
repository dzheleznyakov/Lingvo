package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary

class XmlReader {
    public static final String VERSION = "v1"
    public static final String TYPE = "xml"
    private File file

    XmlReader(File file) {
        this.file = file
    }

    LearningDictionary read() {
        if (!file.exists())
            throw new PersistenceException("File [" + file.absolutePath + "] is not found")
        def root = new XmlSlurper().parse(file)
        verifyMetadata(root)

        return null
    }

    def read(File file) {
        def root = new XmlSlurper().parse(file)
        Class<?> clazz = getEntityClass(root)
        return clazz.newInstance()
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
