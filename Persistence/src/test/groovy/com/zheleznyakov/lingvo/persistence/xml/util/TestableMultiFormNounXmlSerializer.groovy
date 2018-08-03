package com.zheleznyakov.lingvo.persistence.xml.util

import com.zheleznyakov.lingvo.implementations.TestableMultiFormNoun
import com.zheleznyakov.lingvo.persistence.xml.serializers.GrammaticalWordXmlSerializer
import com.zheleznyakov.lingvo.persistence.xml.serializers.basic.ObjectXmlSerializer
import groovy.xml.MarkupBuilder

@SuppressWarnings("GroovyUnusedDeclaration")
class TestableMultiFormNounXmlSerializer implements GrammaticalWordXmlSerializer<TestableMultiFormNoun> {
    @Override
    void serialize(TestableMultiFormNoun value, MarkupBuilder builder, String tag, def attributes, def serializer) {
        if (attributes == null)
            attributes = [:]
        attributes['class'] = TestableMultiFormNoun.canonicalName
        def objectSerializer = new Object().withTraits(ObjectXmlSerializer)
        objectSerializer.serialize(value, builder, tag, attributes, serializer)
    }

    @Override
    Class<TestableMultiFormNoun> getSerializableClass() {
        return TestableMultiFormNoun
    }
}
