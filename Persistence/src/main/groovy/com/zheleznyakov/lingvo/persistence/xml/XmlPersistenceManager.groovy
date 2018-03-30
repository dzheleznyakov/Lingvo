package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import groovy.xml.MarkupBuilder

class XmlPersistenceManager {

    void persist(LearningDictionary dictionary) {
        String directoryPath = ZhConfigFactory.get().getString("persistence.xml.root")
        ensureDirectoryExists(directoryPath)
        FileWriter fileWriter = ["$directoryPath/${dictionary.getName()}.xml"]
        MarkupBuilder xmlBuilder = [fileWriter]
        xmlBuilder.persistenceManager(version: 'v1') {
            dictionary {
                language("${dictionary.language}")
            }
        }
    }

    private void ensureDirectoryExists(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        boolean directoryCreated = true;
        if (!directory.exists())
            directoryCreated = directory.mkdirs();
        if (!directoryCreated)
            throw new IOException("Failed to create directory");
    }

}
