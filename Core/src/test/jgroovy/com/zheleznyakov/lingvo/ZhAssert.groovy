package com.zheleznyakov.lingvo

import spock.lang.Specification

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException

import static org.junit.Assert.assertTrue
import static org.junit.Assert.fail

class ZhAssert extends Specification {

    static <T> void assertIllegalAccess(Class<T> clazz, String errorMessage) {
        Constructor<T> constructor = clazz.getDeclaredConstructor()
        constructor.setAccessible(true)
        try {
            constructor.newInstance()
            fail()
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause()
            assertTrue(cause instanceof IllegalAccessException)
            assertTrue(cause.message == errorMessage)
        }
    }

}
