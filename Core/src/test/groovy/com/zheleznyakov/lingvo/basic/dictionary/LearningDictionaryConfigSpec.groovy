package com.zheleznyakov.lingvo.basic.dictionary

import spock.lang.Specification


class LearningDictionaryConfigSpec extends Specification {

    def "Test the default config"() {
        when: "the default config is created"
        LearningDictionaryConfig config = LearningDictionaryConfig.getDefault()

        then: "the default parameters are as expected"
        with(config) {
            it.mode == LearningDictionaryConfig.Mode.FORWARD
            it.maxLearnCount == 30
            it.strict == false
        }
    }

    def "Update learning dictionary config mode"() {
        given: "a learning dictionary config"
        def defaultConfig = LearningDictionaryConfig.getDefault()

        expect: "the current mode to be FORWARD"
        defaultConfig.mode == LearningDictionaryConfig.Mode.FORWARD

        when: "the mode is changed to BACKWARD"
        defaultConfig.setMode(LearningDictionaryConfig.Mode.BACKWARD)

        then: "the new current mode is BACKWARD"
        defaultConfig.mode == LearningDictionaryConfig.Mode.BACKWARD
    }

    def "Update learning dictionary config maxLearnCount"() {
        given: "a learning dictionary config"
        def defaultConfig = LearningDictionaryConfig.getDefault()

        expect: "the current maxLearnCount to be 30"
        defaultConfig.maxLearnCount == 30

        when: "the maxLearnCount is set to 10"
        defaultConfig.maxLearnCount = 10

        then: "the new maxLearnCount is 10"
        defaultConfig.maxLearnCount == 10
    }

}