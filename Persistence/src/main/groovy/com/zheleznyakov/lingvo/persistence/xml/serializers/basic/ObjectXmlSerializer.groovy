package com.zheleznyakov.lingvo.persistence.xml.serializers.basic

import com.zheleznyakov.lingvo.basic.persistence.Persistable
import com.zheleznyakov.lingvo.persistence.PersistenceRegistry
import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
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
                serialize(field.get(entity), builder, field.name)
                field.accessible = isAccessible
            }
        }
    }

}
