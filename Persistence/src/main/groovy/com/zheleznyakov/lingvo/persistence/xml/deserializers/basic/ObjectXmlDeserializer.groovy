package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

import com.zheleznyakov.lingvo.persistence.PersistenceHelper
import com.zheleznyakov.lingvo.persistence.xml.deserializers.XmlDeserializer
import groovy.util.slurpersupport.GPathResult

import java.lang.reflect.Field

trait ObjectXmlDeserializer implements XmlDeserializer<Object> {
    @Override
    Object deserialize(GPathResult node, Class<Object> clazz, def serializationContext) {
        def entity = clazz.newInstance()
        def fields = PersistenceHelper.getPersistableFields(clazz)
        fields.each { field ->
            def fieldValue = getFieldValue(field, node, serializationContext)
            setFieldValue(entity, field, fieldValue)
        }
        return entity
    }

    private getFieldValue(Field field, GPathResult node, def serializationContext) {
        Class<?> fieldType = field.type
        String fieldName = field.name
        def fieldNode = node."$fieldName"
        def des = serializationContext.getOrDefault(fieldType, serializationContext.get(Object.class))
        return des.deserialize(fieldNode, fieldType, serializationContext)
    }

    private void setFieldValue(def object, Field field, def value) {
        boolean accessible = field.accessible
        field.accessible = true
        field.set(object, value)
        field.accessible = accessible
    }
}