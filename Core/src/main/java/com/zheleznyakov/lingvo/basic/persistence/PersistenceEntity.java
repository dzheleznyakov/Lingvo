package com.zheleznyakov.lingvo.basic.persistence;

import java.util.Map;

public interface PersistenceEntity {
    Class<?> getEntityClass();

    default void addField(String key, PersistenceEntity persistenceEntity) {
        getFields().put(key, persistenceEntity);
    };

    Map<String, PersistenceEntity> getFields();
}
