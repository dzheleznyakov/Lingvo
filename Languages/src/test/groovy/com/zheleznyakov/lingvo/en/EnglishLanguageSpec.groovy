package com.zheleznyakov.lingvo.en

import com.zheleznyakov.lingvo.basic.Language
import spock.lang.Specification
import spock.lang.Unroll

class EnglishLanguageSpec extends Specification {
    private static final Language ENGLISH = EnglishLanguage.get()

    def "Test English Language basic properties"() {
        expect: "the English name and code are returned correctly"
        ENGLISH.name() == "English"
        ENGLISH.code() == "En"
    }

    @Unroll
    def "Test whether string [#string] is legal for English: #legal"() {
        expect: "the string is tested correctly"
        ENGLISH.isStringLegal(string) == legal

        where: "the parameters are as follows"
        string  || legal
        "abc"   || true
        "ab1"   || false
        "Abc"   || true
        "ab's"  || true
        "ab''s" || false
        "'abc"  || true
        "'ABC"  || true
        "ab-cd" || true
        "-abc"  || false
        "ab-'"  || false
        "ab'-c" || false
        "abc-"  || false
    }

}
