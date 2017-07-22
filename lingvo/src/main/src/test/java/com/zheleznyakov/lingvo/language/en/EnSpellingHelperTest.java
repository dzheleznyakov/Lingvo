package com.zheleznyakov.lingvo.language.en;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class EnSpellingHelperTest {
    @Test
    public void createClassInstance_Throws() throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        Constructor<EnSpellingHelper> constructor = EnSpellingHelper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
            fail();
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            assertTrue(cause instanceof IllegalAccessException);
            assertTrue(cause.getMessage().equals("This class is a static helper; it is not supposed to be instantiated"));
        }
    }
}