package com.zheleznyakov.lingvo.persistence.xml

import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.persistence.Persistable
import com.zheleznyakov.lingvo.basic.persistence.PersistableMetadata
import com.zheleznyakov.lingvo.persistence.PersistenceRegistry
import com.zheleznyakov.lingvo.util.Util
import groovy.transform.PackageScope
import groovy.xml.MarkupBuilder

import java.lang.reflect.Field

@PackageScope
class XmlWriter {
    private static Set<Class<?>> primitiveClasses = ImmutableSet.of(
            Boolean, Byte, Short, Character, Integer, Long, Float, Double, String,
            int, byte, short, char, int, long, float, double)

    private final MarkupBuilder xmlBuilder

    XmlWriter(Writer writer) {
        xmlBuilder = [writer]
    }

    void write(LearningDictionary dictionary) {
        xmlBuilder.root() {
            writePersistenceMetadata(xmlBuilder)
            doWrite(dictionary, xmlBuilder, "dictionary")
        }
    }

    void write(Object entity) {
        doWrite(entity, xmlBuilder, entity.class.simpleName)
    }

    private static void doWrite(def entity, def builder, String tag) {
        Class<?> entityClass = entity.getClass()
        if (primitiveClasses.contains(entityClass))
            writePrimitive(entity, builder, tag)
        else if (Enum.isAssignableFrom(entityClass))
            writeEnum(entity, builder, tag)
        else if (entityClass.isArray())
            writeArray(entity, builder, tag)
        else if (Collection.isAssignableFrom(entityClass))
            writeCollection(entity, builder, tag)
        else if (Map.isAssignableFrom(entityClass))
            writeMap(entity, builder, tag)
        else
            writeObject(entity, builder, tag)
    }

    private static void writePrimitive(value, def builder, String tag) {
        builder."$tag"(value)
    }

    private static void writeEnum(Enum value, def builder, String tag) {
        builder."$tag"(value.name())
    }

    private static void writeArray(value, def builder, String tag) {
        writeCollection(value.toList(), builder, tag)
    }

    private static void writeCollection(Collection<?> values, def builder, String tag) {
        builder."$tag" {
            values.each { value -> doWrite(value, builder, value.getClass().simpleName) }
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
        def attributes = PersistenceRegistry.hasPersistableMetadata(entityClass) ? ['class': entityClass.simpleName] : [:]
        builder."$tag"( attributes ) {
            PersistenceRegistry.getPersistableFields(entityClass).each { field ->
                boolean isAccessible = field.accessible
                field.accessible = true
                doWrite(getValueToPersist(field, entity), builder, field.name)
                field.accessible = isAccessible
            }
        }
    }

    private static Object getValueToPersist(Field field, Object entity) {
        String value = field.getAnnotation(Persistable).value()
        return (Util.isBlank(value)) ? field.get(entity) : field.get(entity)."$value"()
    }

    private void writePersistenceMetadata(builder) {
        builder.persistenceManager(version: 'v1', type: 'xml')
    }
}
