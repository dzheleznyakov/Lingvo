package com.zheleznyakov.lingvo.basic.persistence;

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary;
import com.zheleznyakov.lingvo.basic.persistence.converters.LearningDictionaryConverter;

public class LearningDictionaryDao {
    private final PersistenceManager persistenceManager;

    public LearningDictionaryDao(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    public void persist(LearningDictionary dictionary) {
        persistenceManager.persist(new LearningDictionaryConverter().convert(dictionary));
    }
}
