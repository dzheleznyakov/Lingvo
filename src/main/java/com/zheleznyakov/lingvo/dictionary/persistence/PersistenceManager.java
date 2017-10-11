package com.zheleznyakov.lingvo.dictionary.persistence;

import java.io.IOException;

import com.zheleznyakov.lingvo.dictionary.Dictionary;

public interface PersistenceManager {
    <T extends Dictionary> T load(Class<T> dictionaryClass, String fileName) throws IOException, ClassNotFoundException;

    <T extends Dictionary> void persist(T dictionary, String fileName) throws IOException;
}