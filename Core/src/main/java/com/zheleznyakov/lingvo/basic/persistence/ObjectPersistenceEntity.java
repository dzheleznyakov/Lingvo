package com.zheleznyakov.lingvo.basic.persistence;

import java.util.HashMap;
import java.util.Map;

public class ObjectPersistenceEntity implements PersistenceEntity {
    private final Class<?> entityClass;
    private final Map<String, PersistenceEntity> fields = new HashMap<>();

    public ObjectPersistenceEntity(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Override
    public Map<String, PersistenceEntity> getFields() {
        return fields;
    }
}
