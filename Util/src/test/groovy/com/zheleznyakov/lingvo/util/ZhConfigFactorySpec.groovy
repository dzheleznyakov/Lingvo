package com.zheleznyakov.lingvo.util

import spock.lang.Specification
import spock.lang.Unroll

import static com.zheleznyakov.lingvo.util.ZhAssert.assertIllegalAccess

class ZhConfigFactorySpec extends Specification {
    def "Config Factory load config correctly" () {
        expect: "config to be loaded"
        ZhConfigFactory.get().getString("testKey") == "Test value"
    }

    @Unroll
    def "Throw when trying to create instance for a static class -- #clazz.simpleName"() {
        expect:
        assertIllegalAccess(clazz, "This class is a static helper; it is not supposed to be instantiated")

        where:
        clazz               | _
        ZhConfigFactory.class | _
    }
}
