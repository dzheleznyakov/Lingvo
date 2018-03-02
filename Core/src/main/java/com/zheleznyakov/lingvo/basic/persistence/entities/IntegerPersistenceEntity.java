package com.zheleznyakov.lingvo.basic.persistence.entities;

public class IntegerPersistenceEntity implements PersistenceEntity {
    private final int value;

    public IntegerPersistenceEntity(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
