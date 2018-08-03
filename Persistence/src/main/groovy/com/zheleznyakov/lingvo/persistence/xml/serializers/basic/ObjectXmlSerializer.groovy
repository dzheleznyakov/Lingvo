package com.zheleznyakov.lingvo.persistence.xml.serializers.basic

import com.zheleznyakov.lingvo.persistence.PersistenceHelper
import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

class ObjectXmlSerializer implements XmlSerializer<Object> {
    @Override
    void serialize(Object entity, MarkupBuilder builder, String tag, def attributes, def serializer) {
        def entityClass = entity.getClass()
        builder."$tag"(attributes) {
            PersistenceHelper.getPersistableFields(entityClass).each { field ->
                boolean isAccessible = field.accessible
                field.accessible = true
                def value = field.get(entity)
                serializer.serialize(value, builder, field.name, [:])
                field.accessible = isAccessible
            }
        }
    }
}
