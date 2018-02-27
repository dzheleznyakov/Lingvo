package com.zheleznyakov.lingvo.basic.persistence;

public abstract class Dao<T> {
    private final PersistenceManager persistenceManager;

    protected Dao(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    public void persist(T object) {
        persistenceManager.persist(new PersistenceEntity());
    }
}
    