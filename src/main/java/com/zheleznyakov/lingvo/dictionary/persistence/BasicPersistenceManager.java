package com.zheleznyakov.lingvo.dictionary.persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.dictionary.Dictionary;
import com.zheleznyakov.lingvo.language.Language;

public class BasicPersistenceManager implements PersistenceManager{

    public static final String EXTENSION = ".dic";

    @Override
    public void persist(Dictionary dictionary, String fileName) throws IOException {
        try (ObjectOutputStream out = getOutputStream(fileName)) {
            out.writeUTF(dictionary.getLanguage().name());
            out.writeObject(dictionary.asMap());
        }
    }

    @NotNull
    private ObjectOutputStream getOutputStream(String fileName) throws IOException {
        return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName + EXTENSION)));
    }

    @Override
    public Dictionary load(String fileName) throws IOException, ClassNotFoundException {
        Dictionary dictionary;
        try (ObjectInputStream input = getInputStream(fileName)) {
            Language language = Language.valueOf(input.readUTF());
            Map<Word, String> words = (Map<Word, String>) input.readObject();
            dictionary = new Dictionary(language);
            words.entrySet().forEach(entry -> dictionary.add(entry.getKey(), entry.getValue()));
        }
        return dictionary;
    }

    private ObjectInputStream getInputStream(String fileName) throws IOException {
        return new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName + EXTENSION)));
    }
}
