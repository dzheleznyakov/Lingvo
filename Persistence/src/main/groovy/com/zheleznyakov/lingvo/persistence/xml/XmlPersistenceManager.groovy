package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord
import com.zheleznyakov.lingvo.basic.words.Language
import com.zheleznyakov.lingvo.persistence.PersistenceHelper
import com.zheleznyakov.lingvo.persistence.xml.deserializers.domain.LearningDictionaryXmlDeserializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.GrammaticalWordXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.domain.LanguageXmlSerializer
import com.zheleznyakov.lingvo.util.ZhConfigFactory

class XmlPersistenceManager {

    void persist(LearningDictionary dictionary) {
        String directoryPath = "${ZhConfigFactory.get().getString("persistence.xml.root")}/${dictionary.language.code()}/xml"
        ensureDirectoryExists(directoryPath)

        FileWriter writer = ["$directoryPath/${dictionary.getName()}.xml"]
        def additionalSerializers = [:]
        additionalSerializers[(Language)] = LanguageXmlSerializer
        PersistenceHelper.wordSerializers.each { ser ->
            def serializableClass = findSerializableClass(ser)
            additionalSerializers[(serializableClass)] = ser
        }
        XmlWriter.with(writer, additionalSerializers).write(dictionary)
    }

    private static Class<? extends GrammaticalWord> findSerializableClass(ser) {
        return new Object().withTraits(ser).serializableClass
    }

    private static void ensureDirectoryExists(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        boolean directoryCreated = true;
        if (!directory.exists())
            directoryCreated = directory.mkdirs();
        if (!directoryCreated)
            throw new IOException("Failed to create directory");
    }

    LearningDictionary load(File file) {
        return new XmlReader([(LearningDictionary): LearningDictionaryXmlDeserializer]).read(file)
    }

}
