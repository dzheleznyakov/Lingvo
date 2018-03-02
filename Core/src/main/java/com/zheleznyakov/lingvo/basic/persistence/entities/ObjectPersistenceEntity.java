package com.zheleznyakov.lingvo.basic.persistence.entities;

import java.util.HashMap;
import java.util.Map;

public class ObjectPersistenceEntity implements PersistenceEntity {
    private final Class<?> entityClass;
    private final Map<String, PersistenceEntity> fields = new HashMap<>();

    public ObjectPersistenceEntity(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public Map<String, PersistenceEntity> getFields() {
        return fields;
    }

    public void addField(String key, PersistenceEntity persistenceEntity) {
        fields.put(key, persistenceEntity);
    }
}
