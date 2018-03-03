package com.zheleznyakov.lingvo.basic.persistence.converters;

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary;
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig;
import com.zheleznyakov.lingvo.basic.dictionary.Record;
import com.zheleznyakov.lingvo.basic.persistence.entities.ListPersistenceEntity;
import com.zheleznyakov.lingvo.basic.persistence.entities.PersistenceEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConvertingWorkshop {
    private final static Map<Class<?>, Converter<?>> handlers = new HashMap<>();

    static {
        registerHandler(LearningDictionary.class, new LearningDictionaryConverter());
        registerHandler(LearningDictionaryConfig.class, new LearningDictionaryConfigConverter());
        registerHandler(Record.class, new RecordConverter());
    }

    @SuppressWarnings("unchecked")
    public static <E> PersistenceEntity convert(E object) {
        Converter<E> converter = (Converter<E>) handlers.get(object.getClass());
        return converter.convert(object);
    }

    @SuppressWarnings("unchecked")
    public static <E> ListPersistenceEntity convert(Collection<E> objects) {
        return new ListPersistenceEntity(objects);
    }

    public static <E> void registerHandler(Class<E> entityClass, Converter<E> entityConverter) {
        handlers.put(entityClass, entityConverter);
    }
}
