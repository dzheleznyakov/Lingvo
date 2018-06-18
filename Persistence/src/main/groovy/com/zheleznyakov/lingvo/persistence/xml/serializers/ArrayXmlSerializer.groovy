package com.zheleznyakov.lingvo.persistence.xml.serializers

import groovy.xml.MarkupBuilder

trait ArrayXmlSerializer implements XmlSerializer<Object[]> {
    @Override
    void serialize(Object[] values, MarkupBuilder builder, String tag) {
        serialize(values.toList(), builder, tag)
    }

    void serialize(boolean[] values, MarkupBuilder builder, String tag) {
        serialize(values.toList(), builder, tag)
    }

    void serialize(char[] values, MarkupBuilder builder, String tag) {
        serialize(values.toList(), builder, tag)
    }

    void serialize(byte[] values, MarkupBuilder builder, String tag) {
        serialize(values.toList(), builder, tag)
    }

    void serialize(short[] values, MarkupBuilder builder, String tag) {
        serialize(values.toList(), builder, tag)
    }

    void serialize(int[] values, MarkupBuilder builder, String tag) {
        serialize(values.toList(), builder, tag)
    }

    void serialize(long[] values, MarkupBuilder builder, String tag) {
        serialize(values.toList(), builder, tag)
    }

    void serialize(float[] values, MarkupBuilder builder, String tag) {
        serialize(values.toList(), builder, tag)
    }

    void serialize(double[] values, MarkupBuilder builder, String tag) {
        serialize(values.toList(), builder, tag)
    }
}