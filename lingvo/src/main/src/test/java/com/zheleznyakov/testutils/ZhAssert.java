package com.zheleznyakov.testutils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class ZhAssert {

    private ZhAssert() {
    }

    public static void assertIllegalAccess(Constructor<Object> constructor, String errorMessage) throws IllegalAccessException, InstantiationException {
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
