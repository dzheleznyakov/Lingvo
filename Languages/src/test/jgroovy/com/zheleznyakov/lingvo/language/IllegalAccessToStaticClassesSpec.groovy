package com.zheleznyakov.lingvo.language

import com.zheleznyakov.lingvo.en.word.EnSpellingHelper
import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Constructor

import static com.zheleznyakov.testutils.ZhAssert.assertIllegalAccess

class IllegalAccessToStaticClassesSpec extends Specification {

    @Unroll
    def "Throw when trying to create instance for a static class -- #clazz.simpleName"() {
        expect:
        Constructor<Object> constructor = clazz.getDeclaredConstructor()
        assertIllegalAccess(constructor, "This class is a static helper; it is not supposed to be instantiated")

        where:
        clazz                  | _
        EnSpellingHelper.class | _
    }
}