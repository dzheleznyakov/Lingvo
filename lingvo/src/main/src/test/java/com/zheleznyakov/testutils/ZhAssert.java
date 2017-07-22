package com.zheleznyakov.testutils;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ZhAssert {
    public static <T> void assertIllegalAccess(Constructor<T> constructor, String errorMessage) throws IllegalAccessException, InstantiationException {
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
            fail();
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            assertTrue(cause instanceof IllegalAccessException);
            assertTrue(cause.getMessage().equals(errorMessage));
        }
    }
}
