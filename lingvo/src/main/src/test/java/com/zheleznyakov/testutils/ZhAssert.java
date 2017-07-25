package com.zheleznyakov.testutils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.zheleznyakov.lingvo.basic.Word;

public abstract class ZhAssert {

    private ZhAssert() {
    }

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

    public static void assertWordForms(Word word, String... expected) {
        assertArrayEquals(expected, word.getForms());
    }

    public static void assertWordFormsFull(Word word, String... expected) {
        assertArrayEquals(expected, word.getFormsFull());
    }
}
