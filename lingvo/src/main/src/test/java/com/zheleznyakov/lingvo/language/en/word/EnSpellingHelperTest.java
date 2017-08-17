package com.zheleznyakov.lingvo.language.en.word;

import static com.zheleznyakov.testutils.ZhAssert.assertIllegalAccess;

import java.lang.reflect.Constructor;

import org.junit.Test;

public class EnSpellingHelperTest {
    @Test
    public void createClassInstance_Throws() throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        Constructor<EnSpellingHelper> constructor = EnSpellingHelper.class.getDeclaredConstructor();
        assertIllegalAccess(constructor, "This class is a static helper; it is not supposed to be instantiated");
    }
}