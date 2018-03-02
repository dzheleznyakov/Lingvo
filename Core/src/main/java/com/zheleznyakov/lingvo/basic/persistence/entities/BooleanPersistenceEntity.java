package com.zheleznyakov.lingvo.basic.persistence.entities;

public class BooleanPersistenceEntity implements PersistenceEntity {
    private final boolean value;

    public BooleanPersistenceEntity(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }
}
