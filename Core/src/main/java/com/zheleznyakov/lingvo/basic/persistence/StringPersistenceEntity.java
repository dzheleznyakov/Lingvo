package com.zheleznyakov.lingvo.basic.persistence;

import com.zheleznyakov.lingvo.basic.persistence.entities.PersistenceEntity;

public class StringPersistenceEntity implements PersistenceEntity {
    private final String value;

    public StringPersistenceEntity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
