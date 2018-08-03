package com.zheleznyakov.lingvo.persistence.xml.serializers

import com.zheleznyakov.lingvo.basic.words.GrammaticalWord

interface GrammaticalWordXmlSerializer<E extends GrammaticalWord> extends XmlSerializer<E> {
    Class<E> getSerializableClass()
}