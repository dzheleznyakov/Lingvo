package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig
import com.zheleznyakov.lingvo.basic.persistence.Persistable
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord
import groovy.transform.PackageScope
import groovy.xml.MarkupBuilder

import java.lang.reflect.Field

@PackageScope
class XmlWriter {
    private final MarkupBuilder xmlBuilder
    private LearningDictionary dictionary

    XmlWriter(FileWriter writer) {
        xmlBuilder = [writer]
    }

    void write(LearningDictionary dictionary) {
        this.dictionary = dictionary
        xmlBuilder.root() {
            buildPersistenceMetadata(xmlBuilder)
            buildDictionary(xmlBuilder)
        }
    }

    private void buildPersistenceMetadata(builder) {
        builder.persistenceManager(version: 'v1', type: 'xml')
    }

    private void buildDictionary(builder) {
        builder.dictionary {
            buildConfig(builder)
            name("${dictionary.name}")
            language("${dictionary.language}")
            buildRecords(builder)
        }
    }

    private void buildConfig(builder) {
        def config = dictionary.config
        builder.config() {
            maxLearnCount(config.maxLearnCount)
            strict(config.strict)
            mode(config.mode.toString())
        }
    }

    private void buildRecords(builder) {
        builder.records() {
            dictionary.records.each { rec -> buildRecord(builder, rec) }
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

    private <E extends GrammaticalWord> void buildWord(builder, E word) {
        Class<E> wordClass = word.class
        def accessibleFieldsToPersist = Arrays.stream(wordClass.declaredFields)
                .filter { Field field -> field.getAnnotation(Persistable) != null }
                .peek { Field field -> field.setAccessible(true) }
                .collect(ImmutableSet.toImmutableSet())

        builder.word {
            'class'(wordClass.simpleName)
            mainForm("${word.mainForm}")
            accessibleFieldsToPersist.each { Field field ->
                "${field.name}"(field.get(word).toString())
            }
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
