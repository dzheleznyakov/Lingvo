package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.persistence.PersistenceRegistry
import groovy.transform.PackageScope
import groovy.xml.MarkupBuilder

@PackageScope
class XmlWriter {
    private static Set<Class<?>> primitiveClasses = ImmutableSet.of(
            Boolean, Byte, Short, Character, Integer, Long, Float, Double, String,
            int, byte, short, char, int, long, float, double)

    private final MarkupBuilder xmlBuilder

    XmlWriter(Writer writer) {
        xmlBuilder = [writer]
    }

    void write(Object entity) {
        doWrite(entity, xmlBuilder, entity.class.simpleName)
    }

    private static void doWrite(def entity, def builder, String tag) {
        Class<?> entityClass = entity.getClass()
        if (primitiveClasses.contains(entityClass))
            writePrimitive(entity, builder, tag)
        else if (Collection.isAssignableFrom(entityClass))
            writeCollection(entity, builder, tag)
        else if (Map.isAssignableFrom(entityClass))
            writeMap(entity, builder, tag)
        else
            writeObject(entity, builder, tag)
    }

    private static void writePrimitive(value, def builder, String tag) {
        builder."$tag"('' + value)
    }

    private static void writeCollection(Collection<?> values, def builder, String tag) {
        builder."$tag" {
            values.each { value ->
                doWrite(value, builder, value.getClass().simpleName) }
        }
    }

    private static void writeMap(Map<?, ?> map, def builder, String tag) {
        builder."$tag" {
            map.entrySet().each { en ->
                entry() {
                    doWrite(en.key, builder, en.key.getClass().simpleName)
                    doWrite(en.value, builder, en.value.getClass().simpleName)
                }
            }
        }
    }

    private static void writeObject(Object entity, def builder, String tag) {
        def entityClass = entity.getClass()
        builder."$tag" {
            PersistenceRegistry.getPersistableFields(entityClass).each { field ->
                boolean isAccessible = field.accessible
                field.accessible = true
                doWrite(field.get(entity), builder, field.name)
                field.accessible = isAccessible
            }
        }
    }

//    void write(LearningDictionary dictionary) {
//        this.dictionary = dictionary
//        xmlBuilder.root() {
//            buildPersistenceMetadata(xmlBuilder)
//            buildDictionary(xmlBuilder)
//        }
//    }
//
//    private void buildPersistenceMetadata(builder) {
//        builder.persistenceManager(version: 'v1', type: 'xml')
//    }
//
//    private void buildDictionary(builder) {
//        builder.dictionary {
//            buildConfig(builder)
//            name("${dictionary.name}")
//            language("${dictionary.language}")
//            buildRecords(builder)
//        }
//    }
//
//    private void buildConfig(builder) {
//        def config = dictionary.config
//        builder.config() {
//            maxLearnCount(config.maxLearnCount)
//            strict(config.strict)
//            mode(config.mode.toString())
//        }
//    }
//
//    private void buildRecords(builder) {
//        builder.records() {
//            dictionary.records.each { rec -> buildRecord(builder, rec) }
//        }
//    }
//
//    private void buildRecord(builder, rec) {
//        builder.record() {
//            buildWord(builder, rec.word)
//            description(rec.description)
//            transcription(rec.transcription)
//            buildUsageExamples(builder, rec.examples)
//        }
//    }
//
//    private <E extends GrammaticalWord> void buildWord(builder, E word) {
//        Class<E> wordClass = word.class
//        builder.word {
//            'class'(wordClass.simpleName)
//            mainForm("${word.mainForm}")
//            PersistenceRegistry.getPersistableFields(wordClass).each { Field field ->
//                "${field.name}"(field.get(word).toString())
//            }
//        }
//    }
//
//    private void buildUsageExamples(builder, usageExamples) {
//        builder.usageExamples {
//            usageExamples.each { ex ->
//                usageExample {
//                    example(ex.example)
//                    translation(ex.translation)
//                }
//            }
//        }
//    }
}
