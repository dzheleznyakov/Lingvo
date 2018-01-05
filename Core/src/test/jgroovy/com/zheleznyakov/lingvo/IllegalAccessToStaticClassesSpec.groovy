package com.zheleznyakov.lingvo

import com.zheleznyakov.lingvo.basic.util.WordFormatter
import com.zheleznyakov.lingvo.util.ZhAssert
import spock.lang.Specification
import spock.lang.Unroll

class IllegalAccessToStaticClassesSpec extends Specification {

    @Unroll
    def "Throw when trying to create instance for a static class -- #clazz.simpleName"() {
        expect:
        ZhAssert.assertIllegalAccess(clazz, "This class is a static helper; it is not supposed to be instantiated")

        where:
        clazz               | _
        WordFormatter.class | _
    }

    def "Fail"() {
        expect: false
    }
}