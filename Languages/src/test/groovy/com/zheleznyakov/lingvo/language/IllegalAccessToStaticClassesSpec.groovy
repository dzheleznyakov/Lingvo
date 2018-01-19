package com.zheleznyakov.lingvo.language

import com.zheleznyakov.lingvo.en.word.EnSpellingHelper
import com.zheleznyakov.lingvo.util.ZhAssert
import spock.lang.Specification
import spock.lang.Unroll

class IllegalAccessToStaticClassesSpec extends Specification {

//    @Unroll
//    def "Throw when trying to create instance for a static class -- #clazz.simpleName"() {
//        expect:
//        Constructor<Object> constructor = clazz.getDeclaredConstructor()
//        ZhAssert.assertIllegalAccess(constructor, "This class is a static helper; it is not supposed to be instantiated")
//
//        where:
//        clazz                  | _
//        EnSpellingHelper.class | _
//    }

//    def "Throw when trying to create instance for a static class -- EnSpellingHelper.class"() {
//        expect:
//        ZhAssert.assertIllegalAccess(EnSpellingHelperHAccessHelper.getEnSpellingHelperClass(), "This class is a static helper; it is not supposed to be instantiated")
//    }

    @Unroll
    def "Throw when trying to create instance for a static class -- #clazz.simpleName"() {
        expect:
        ZhAssert.assertIllegalAccess(clazz, "This class is a static helper; it is not supposed to be instantiated")

        where:
        clazz                  | _
        EnSpellingHelper.class | _
    }
}