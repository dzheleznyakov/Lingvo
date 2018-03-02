package com.zheleznyakov.lingvo.basic.persistence;

import com.zheleznyakov.lingvo.basic.persistence.entities.PersistenceEntity;

public interface PersistenceManager {
    void persist(PersistenceEntity entity);
}
