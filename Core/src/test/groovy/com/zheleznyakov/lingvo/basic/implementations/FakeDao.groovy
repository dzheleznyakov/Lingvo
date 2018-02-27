package com.zheleznyakov.lingvo.basic.implementations

import com.zheleznyakov.lingvo.basic.persistence.Dao
import com.zheleznyakov.lingvo.basic.persistence.PersistenceManager

class FakeDao<T> extends Dao<T> {
    protected FakeDao(PersistenceManager persistenceManager) {
        super(persistenceManager)
    }
}
