package com.zheleznyakov.lingvo.dictionary.persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.dictionary.Dictionary;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.util.ZhConfigFactory;

public class BasicPersistenceManager implements PersistenceManager {
    private static final String DICTIONARY_ROOT_FOLDER = ZhConfigFactory.get().getString("languageDirRoot");

    @Override
    public <T extends Dictionary> void persist(T dictionary) throws IOException {
        String path = sanitizePath(DICTIONARY_ROOT_FOLDER) + dictionary.getLanguage().toLowerCase() + "/";
        ensureThatFolderExists(path);
        String fileName = path + dictionary.getTitle() + FILE_EXTENSION_SUPPLIER.apply(dictionary.getClass());
        try (ObjectOutputStream out = getOutputStream(fileName)) {
            out.writeObject(dictionary);
        }
    }

    private static void ensureThatFolderExists(String path) {
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdirs();
    }

    @NotNull
    private static String sanitizePath(String path) {
        return path + (path.endsWith("/") ? "" : "/");
    }

    @NotNull
    private static ObjectOutputStream getOutputStream(String fileName) throws IOException {
        return new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Dictionary> T load(Class<T> dictionaryClass, Language language, String fileName) throws IOException, ClassNotFoundException {
        String pathToFile = sanitizePath(DICTIONARY_ROOT_FOLDER) + language.toLowerCase() + "/" + fileName;
        try (ObjectInputStream input = getInputStream(pathToFile)) {
            return (T) input.readObject();
        }
    }

    @NotNull
    private static ObjectInputStream getInputStream(String fileName) throws IOException {
        return new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
    }
}
