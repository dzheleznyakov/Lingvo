package com.zheleznyakov.lingvo.persistence.xml.deserializers.basic

import com.zheleznyakov.lingvo.persistence.PersistenceHelper
import com.zheleznyakov.lingvo.persistence.xml.deserializers.BaseXmlDeserializer
import com.zheleznyakov.lingvo.util.Util
import groovy.util.slurpersupport.GPathResult

import java.lang.reflect.Field

trait ObjectXmlDeserializer implements BaseXmlDeserializer<Object> {
    @Override
    Object deserialize(GPathResult node, Class<Object> clazz, def deserializer) {
        def entity = clazz.newInstance()
        def fields = PersistenceHelper.getPersistableFields(clazz)
        fields.each { field ->
            def fieldValue = getFieldValue(field, node, deserializer)
            setFieldValue(entity, field, fieldValue)
        }
        return entity
    }

    private getFieldValue(Field field, GPathResult node, def deserializer) {
        def fieldTypeAttribute = node."$field.name".@type.toString()
        Class<?> fieldType = Util.isBlank(fieldTypeAttribute) ? field.type : Class.forName(fieldTypeAttribute)
        String fieldName = field.name
        def fieldNode = node."$fieldName"
        return deserializer.deserialize(fieldNode, fieldType)
    }

    private void setFieldValue(def object, Field field, def value) {
        boolean accessible = field.accessible
        field.accessible = true
        field.set(object, value)
        field.accessible = accessible
    }
}