package com.zheleznyakov.lingvo.persistence.xml.serializers

import com.zheleznyakov.lingvo.basic.persistence.Persistable
import com.zheleznyakov.lingvo.persistence.PersistenceRegistry
import com.zheleznyakov.lingvo.util.Util
import groovy.xml.MarkupBuilder

import java.lang.reflect.Field

trait ObjectXmlSerializer implements XmlSerializer<Object> {
    @Override
    void serialize(Object entity, MarkupBuilder builder, String tag) {
        def entityClass = entity.getClass()
        builder."$tag"() {
            PersistenceRegistry.getPersistableFields(entityClass).each { field ->
                boolean isAccessible = field.accessible
                field.accessible = true
                serialize(getValueToPersist(field, entity), builder, field.name)
                field.accessible = isAccessible
            }
        }
    }

    private Object getValueToPersist(Field field, Object entity) {
        String value = field.getAnnotation(Persistable).value()
        return (Util.isBlank(value)) ? field.get(entity) : field.get(entity)."$value"()
    }
}
