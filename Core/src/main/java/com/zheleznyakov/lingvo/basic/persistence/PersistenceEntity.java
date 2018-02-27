package com.zheleznyakov.lingvo.basic.persistence;

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary;

public class PersistenceEntity {
    private final Class<?> entityClass = LearningDictionary.class;

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
