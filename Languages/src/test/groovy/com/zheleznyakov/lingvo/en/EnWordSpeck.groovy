package com.zheleznyakov.lingvo.en

import com.zheleznyakov.lingvo.basic.parts.PartOfSpeech
import spock.lang.Specification

class EnWordSpeck extends Specification {

    def "An English word determines its language as English"() {
        given: "an English word"
        EnWord word = new TestableEnglishWord("abc")

        expect: "that it knows about its language"
        word.language == EnglishLanguage.get()
    }

    def "An English word returns its main form"() {
        given: "an English word"
        String mainForm = "abc"
        EnWord word = new TestableEnglishWord(mainForm)

        expect: "it can return its main form"
        word.mainForm == mainForm
    }

    def "If an illegal English string is passed as main form, the exception is thrown"() {
        given: "an illegal English string"
        String string = "abc1"

        when: "an English word is created"
        new TestableEnglishWord(string)

        then: "an IllegalArgumentException is thrown"
        def exception = thrown(IllegalArgumentException)
        exception.message.contains "illegal for English"
    }

    private class TestableEnglishWord extends EnWord {
        TestableEnglishWord(String mainForm) {
            super(mainForm)
        }

        @Override
        String[] getForms() {
            return new String[0]
        }

        @Override
        PartOfSpeech getPartOfSpeech() {
            return null
        }
    }
}
