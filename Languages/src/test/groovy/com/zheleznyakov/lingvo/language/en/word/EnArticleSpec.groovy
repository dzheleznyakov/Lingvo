package com.zheleznyakov.lingvo.language.en.word

import com.zheleznyakov.lingvo.en.word.EnArticle
import spock.lang.Specification
import spock.lang.Unroll

class EnArticleSpec extends Specification {

    def "Throw when creating a fake English article"() {
        expect: "Expect an exception when creating a fake English article"
        try {
            EnArticle.build("abc")
        } catch (ex) {
            ex instanceof IllegalArgumentException
        }
    }

    @Unroll
    def "Test transcription of English articles -- #word"() {
        when: "an English article is created"
        EnArticle article = EnArticle.build(word)

        then: "the transcription of the article is correct"
        article.transcription == "[${transcription}]"

        where: "words and their transcriptions are"
        word     | transcription
        "a"      | "ə"
        "the"    | "ðə"
    }
}
