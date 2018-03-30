package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import groovy.xml.MarkupBuilder
import jdk.nashorn.internal.ir.LexicalContext

class XmlPersistenceManager {
    private LearningDictionary learningDictionary;

    void persist(LearningDictionary learningDictionary) {
        this.learningDictionary = learningDictionary
        String directoryPath = "${ZhConfigFactory.get().getString("persistence.xml.root")}/${learningDictionary.language.code()}/xml"
        ensureDirectoryExists(directoryPath)

        FileWriter writer = ["$directoryPath/${learningDictionary.getName()}.xml"]
        MarkupBuilder xml = [writer]
        xml.root() {
            persistenceManager(version: 'v1', type: 'xml')
            dictionary {
                name("${learningDictionary.name}")
                language("${learningDictionary.language}")
                records() {
                    learningDictionary.records.each { rec ->
                        record()
                    }
                }
            }
        }
    }

    private static void ensureDirectoryExists(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        boolean directoryCreated = true;
        if (!directory.exists())
            directoryCreated = directory.mkdirs();
        if (!directoryCreated)
            throw new IOException("Failed to create directory");
    }

}
