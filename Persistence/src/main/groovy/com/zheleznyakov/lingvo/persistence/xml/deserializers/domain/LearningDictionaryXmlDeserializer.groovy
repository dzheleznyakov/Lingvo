package com.zheleznyakov.lingvo.persistence.xml.deserializers.domain

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.persistence.xml.deserializers.BaseXmlDeserializer
import groovy.util.slurpersupport.GPathResult

trait LearningDictionaryXmlDeserializer implements BaseXmlDeserializer<LearningDictionary> {
    @Override
    LearningDictionary deserialize(GPathResult node, Class<LearningDictionary> clazz, def deserializer) {
        def languageNode = node.dictionary.language
        def nameNode = node.dictionary.name
        verifyAllNodesExist(languageNode, nameNode)
//        LearningDictionary dictionary =
        return null
    }
}
