package com.zheleznyakov.lingvo.dictionary.persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.dictionary.Dictionary;

public class BasicPersistenceManager implements PersistenceManager {

    @Override
    public <T extends Dictionary> void persist(T dictionary, String fileName) throws IOException {
        String extension = dictionary.getClass() == Dictionary.class ? DIC_EXTENSION : LD_EXTENSION;
        try (ObjectOutputStream out = getOutputStream(fileName + extension)) {
            out.writeObject(dictionary);
        }
    }

    @NotNull
    private ObjectOutputStream getOutputStream(String fileName) throws IOException {
        return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Dictionary> T load(Class<T> dictionaryClass, String fileName) throws IOException, ClassNotFoundException {
        String extension = dictionaryClass == Dictionary.class ? DIC_EXTENSION : LD_EXTENSION;
        try (ObjectInputStream input = getInputStream(fileName + extension)) {
            return (T) input.readObject();
        }
    }

    @NotNull
    private ObjectInputStream getInputStream(String fileName) throws IOException {
        return new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
    }
}
