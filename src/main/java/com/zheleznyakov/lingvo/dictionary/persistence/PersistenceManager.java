package com.zheleznyakov.lingvo.dictionary.persistence;

import java.io.IOException;

import com.zheleznyakov.lingvo.dictionary.Dictionary;

public interface PersistenceManager {
    Dictionary load(String fileName) throws IOException, ClassNotFoundException;

    void persist(Dictionary dictionary, String fileName) throws IOException;
}
