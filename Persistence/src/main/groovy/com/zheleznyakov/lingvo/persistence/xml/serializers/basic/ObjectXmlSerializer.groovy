package com.zheleznyakov.lingvo.persistence.xml.serializers.basic

import com.zheleznyakov.lingvo.persistence.PersistenceHelper
import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait ObjectXmlSerializer implements XmlSerializer<Object> {
    @Override
    void serialize(Object entity, MarkupBuilder builder, String tag) {
        def entityClass = entity.getClass()
        builder."$tag"() {
            PersistenceHelper.getPersistableFields(entityClass).each { field ->
                boolean isAccessible = field.accessible
                field.accessible = true
                serialize(field.get(entity), builder, field.name)
                field.accessible = isAccessible
            }
        }
    }

}
