package com.zheleznyakov.testutils;

import java.util.Arrays;
import java.util.Set;

public class ZhSets {

    public static <T extends Set, E> Set<E> newSet(Class<T> setClass, E... elements) throws IllegalAccessException, InstantiationException {
        Set<E> set = (Set<E>) setClass.newInstance();
        set.addAll(Arrays.asList(elements));
        return set;
    }
}
