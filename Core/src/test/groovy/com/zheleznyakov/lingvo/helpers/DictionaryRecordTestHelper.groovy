package com.zheleznyakov.lingvo.helpers

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.Record
import com.zheleznyakov.lingvo.implementations.TestableMultiFormNoun

import java.util.stream.IntStream

class DictionaryRecordTestHelper {
    static void addFullRecordsToDictionary(LearningDictionary dictionary, int numberOfRecords) {
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
