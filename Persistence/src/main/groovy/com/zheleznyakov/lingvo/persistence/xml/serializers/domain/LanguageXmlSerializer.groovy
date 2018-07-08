package com.zheleznyakov.lingvo.persistence.xml.serializers.domain

import com.zheleznyakov.lingvo.basic.words.Language
import com.zheleznyakov.lingvo.persistence.xml.serializers.XmlSerializer
import groovy.xml.MarkupBuilder

trait LanguageXmlSerializer implements XmlSerializer<Language> {
    @Override
    void serialize(Language language, MarkupBuilder builder, String tag, def attributes, def serializer) {
        builder.language(attributes, language.code())
    }
}