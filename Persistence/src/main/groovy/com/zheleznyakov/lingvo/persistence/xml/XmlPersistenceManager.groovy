package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.util.ZhConfigFactory

class XmlPersistenceManager {

    void persist(LearningDictionary dictionary) {
        String directoryPath = "${ZhConfigFactory.get().getString("persistence.xml.root")}/${dictionary.language.code()}/xml"
        ensureDirectoryExists(directoryPath)

        FileWriter writer = ["$directoryPath/${dictionary.getName()}.xml"]
        new XmlWriter(writer).write(dictionary)
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