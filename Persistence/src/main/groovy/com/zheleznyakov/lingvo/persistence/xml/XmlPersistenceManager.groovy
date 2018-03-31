package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import groovy.xml.MarkupBuilder

class XmlPersistenceManager {
    private LearningDictionary learningDictionary;

    void persist(LearningDictionary learningDictionary) {
        this.learningDictionary = learningDictionary

        String directoryPath = "${ZhConfigFactory.get().getString("persistence.xml.root")}/${learningDictionary.language.code()}/xml"
        ensureDirectoryExists(directoryPath)

        FileWriter writer = ["$directoryPath/${learningDictionary.getName()}.xml"]
        MarkupBuilder xmlBuilder = [writer]
        xmlBuilder.root() {
            buildPersistenceMetadata(xmlBuilder)
            buildDictionary(xmlBuilder)
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

    private void buildPersistenceMetadata(builder) {
        builder.persistenceManager(version: 'v1', type: 'xml')
    }

    private void buildDictionary(builder) {
        builder.dictionary {
            name("${learningDictionary.name}")
            language("${learningDictionary.language}")
            buildRecords(builder)
        }
    }

    private void buildRecords(builder) {
        builder.records() {
            learningDictionary.records.each { rec -> buildRecord(builder, rec) }
        }
    }

    private void buildRecord(builder, rec) {
        builder.record() {
            buildWord(builder, rec.word)
            description(rec.description)
            transcription(rec.transcription)
            buildUsageExamples(builder, rec.examples)
        }
    }

    private void buildWord(builder, word) {
        builder.word {
            'class'(word.class.simpleName)
            mainForm("${word.mainForm}")
        }
    }

    private void buildUsageExamples(builder, usageExamples) {
        builder.usageExamples {
            usageExamples.each { ex ->
                usageExample {
                    example(ex.example)
                    translation(ex.translation)
                }
            }
        }
    }
}
