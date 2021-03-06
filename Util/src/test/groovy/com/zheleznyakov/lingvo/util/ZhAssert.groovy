package com.zheleznyakov.lingvo.util

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException

import static org.junit.Assert.assertTrue

class ZhAssert {

    static <T> void assertIllegalAccess(Class<T> clazz, String errorMessage) {
        Constructor<T> constructor = clazz.getDeclaredConstructor()
        constructor.setAccessible(true)
        try {
            constructor.newInstance()
            assert false
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause()
            assertTrue(cause instanceof IllegalAccessException)
            assertTrue(cause.message == errorMessage)
        }
    }

}
