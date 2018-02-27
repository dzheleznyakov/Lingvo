package com.zheleznyakov.lingvo.helpers

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.Record
import com.zheleznyakov.lingvo.basic.implementations.TestableMultiFormNoun

import java.util.stream.IntStream

class TestHelper {
    private static def STATIC_PROPERTIES_FILTER = { it != "default" }


    static def areConfigsEqual(def config1, def config2) {
        def properties1 = config1.properties
        def properties2 = config2.properties
        assert properties1.keySet() == properties2.keySet()
        properties1.keySet().stream()
                .filter(STATIC_PROPERTIES_FILTER)
                .forEach { assert properties1[it] == properties2[it] }
        return true
    }

    static def areConfigsNotEqual(def config1, def config2) {
        def properties1 = config1.properties
        def properties2 = config2.properties
        assert properties1.keySet() == properties2.keySet()
        boolean allPropertiesAreEqual = properties1.keySet().stream()
                .filter(STATIC_PROPERTIES_FILTER)
                .allMatch { (properties1[it] == properties2[it]) }
        assert !allPropertiesAreEqual
        return true
    }

    static void addRecordsToDictionary(LearningDictionary dictionary, int numberOfRecords) {
        IntStream.range(0, numberOfRecords)
                .mapToObj { (it + (char) 'a') as char }
                .map { ["word${it}"] as TestableMultiFormNoun }
                .forEach { addFullRecord(dictionary, it) }
    }

    static def addFullRecord(def dictionary, def word) {
        def mainForm = word.mainForm
        dictionary.record(word, "${mainForm} description")
                .withTranscription("${mainForm} transcription")
                .withUsageExample(["${mainForm} example", "${mainForm} translation"] as Record.UsageExample)
                .add()
    }
}
