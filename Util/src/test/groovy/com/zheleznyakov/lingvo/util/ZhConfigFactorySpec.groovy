package com.zheleznyakov.lingvo.util

import spock.lang.Specification
import spock.lang.Unroll

import static com.zheleznyakov.lingvo.util.ZhAssert.assertIllegalAccess

class ZhConfigFactorySpec extends Specification {
    def "Config Factory load config correctly" () {
        expect: "config to be loaded"
        ZhConfigFactory.get().getString("testKey") == "Test value"
    }

    def "When getting config the second time, the same object is returned"() {
        when: "getting config twice"
        def config1 = ZhConfigFactory.get()
        def config2 = ZhConfigFactory.get()

        then: "the same object returned both times"
        config1.is config2
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
