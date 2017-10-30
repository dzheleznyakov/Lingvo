package com.zheleznyakov.lingvo.dictionary.persistence;

import java.io.IOException;
import java.util.function.Function;

import com.zheleznyakov.lingvo.dictionary.Dictionary;
import com.zheleznyakov.lingvo.language.Language;

public interface PersistenceManager {
    String DIC_EXTENSION = ".dic";
    String LD_EXTENSION = ".ldi";
    Function<Class<? extends Dictionary>, String> FILE_EXTENSION_SUPPLIER =
            (Class<? extends Dictionary> dClass) -> dClass.equals(Dictionary.class) ? DIC_EXTENSION : LD_EXTENSION;

    <T extends Dictionary> T load(Class<T> dictionaryClass, Language language, String fileName) throws IOException, ClassNotFoundException;

    <T extends Dictionary> void persist(T dictionary) throws IOException;
}
