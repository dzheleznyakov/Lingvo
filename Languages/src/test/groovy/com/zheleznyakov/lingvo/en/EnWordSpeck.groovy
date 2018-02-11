package com.zheleznyakov.lingvo.en

import com.zheleznyakov.lingvo.basic.parts.PartOfSpeech
import spock.lang.Specification

class EnWordSpeck extends Specification {

    def "An English word determines its language as English"() {
        given: "an English word"
        EnWord word = new MyEnglishWord("abc")

        expect: "that it knows about its language"
        word.language == EnglishLanguage.get()
    }

    def "An English word returns its main form"() {
        given: "an English word"
        String mainForm = "abc"
        EnWord word = new MyEnglishWord(mainForm)

        expect: "it can return its main form"
        word.mainForm == mainForm
    }

    private class MyEnglishWord extends EnWord {
        MyEnglishWord(String mainForm) {
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
