package com.zheleznyakov.lingvo.persistence.xml.serializers.basic

import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait ArrayXmlSerializer implements XmlSerializer<Object[]> {
    @Override
    void serialize(Object[] values, MarkupBuilder builder, String tag, def attributes, def serializer) {
       builder."$tag"(attributes) {
            values.each { value -> serialize(value, builder, value.getClass().simpleName, [:])}
        }
    }

    void serialize(boolean[] values, MarkupBuilder builder, String tag, def attributes) {
       builder."$tag"(attributes) {
            values.each { value -> serialize(value, builder, value.getClass().simpleName, [:])}
        }
    }

    void serialize(char[] values, MarkupBuilder builder, String tag, def attributes) {
       builder."$tag"(attributes) {
            values.each { value -> serialize(value, builder, value.getClass().simpleName, [:])}
        }
    }

    void serialize(byte[] values, MarkupBuilder builder, String tag, def attributes) {
       builder."$tag"(attributes) {
            values.each { value -> serialize(value, builder, value.getClass().simpleName, [:])}
        }
    }

    void serialize(short[] values, MarkupBuilder builder, String tag, def attributes) {
       builder."$tag"(attributes) {
            values.each { value -> serialize(value, builder, value.getClass().simpleName, [:])}
        }
    }

    void serialize(int[] values, MarkupBuilder builder, String tag, def attributes) {
       builder."$tag"(attributes) {
            values.each { value -> serialize(value, builder, value.getClass().simpleName, [:])}
        }
    }

    void serialize(long[] values, MarkupBuilder builder, String tag, def attributes) {
       builder."$tag"(attributes) {
            values.each { value -> serialize(value, builder, value.getClass().simpleName, [:])}
        }
    }

    void serialize(float[] values, MarkupBuilder builder, String tag, def attributes) {
       builder."$tag"(attributes) {
            values.each { value -> serialize(value, builder, value.getClass().simpleName, [:])}
        }
    }

    void serialize(double[] values, MarkupBuilder builder, String tag, def attributes) {
       builder."$tag"(attributes) {
            values.each { value -> serialize(value, builder, value.getClass().simpleName, [:])}
        }
    }
}