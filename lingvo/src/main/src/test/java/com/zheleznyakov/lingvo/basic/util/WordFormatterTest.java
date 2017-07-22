package com.zheleznyakov.lingvo.basic.util;

import static com.zheleznyakov.testutils.ZhAssert.assertIllegalAccess;

import java.lang.reflect.Constructor;

import org.junit.Test;


public class WordFormatterTest {
    @Test
    public void createClassInstance_Throws() throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        Constructor<WordFormatter> constructor = WordFormatter.class.getDeclaredConstructor();
        assertIllegalAccess(constructor, "This class is a static helper; it is not supposed to be instantiated");
    }
}