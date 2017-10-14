package com.zheleznyakov.lingvo.dictionary.persistence;

public class PersistenceUtil {
    private static PersistenceManager INSTANCE;

    private PersistenceUtil() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    public static PersistenceManager get() {
        if (INSTANCE == null)
            INSTANCE = new BasicPersistenceManager();
        return INSTANCE;
    }
}
