package com.zheleznyakov.lingvo

import com.zheleznyakov.lingvo.dictionary.persistence.PersistenceUtil
import spock.lang.Specification
import spock.lang.Unroll

import static com.zheleznyakov.lingvo.util.ZhAssert.assertIllegalAccess

class IllegalAccessToStaticClassesSpec extends Specification {

    @Unroll
    def "Throw when trying to create instance for a static class -- #clazz.simpleName"() {
        expect:
        assertIllegalAccess(clazz, "This class is a static helper; it is not supposed to be instantiated")

        where:
        clazz                  | _
        PersistenceUtil.class  | _
    }
}