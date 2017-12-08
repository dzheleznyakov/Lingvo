package com.zheleznyakov.lingvo.util

import spock.lang.Specification

class ZhConfigFactorySpec extends Specification {
    def "Config Factory load config correctly" () {
        expect: "config to be loaded"
        ZhConfigFactory.get().getString("testKey") == "Test value"
    }
}
