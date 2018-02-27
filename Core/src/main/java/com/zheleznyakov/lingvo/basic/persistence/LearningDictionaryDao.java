package com.zheleznyakov.lingvo.basic.persistence;

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary;

public class LearningDictionaryDao extends Dao<LearningDictionary> {
    protected LearningDictionaryDao(PersistenceManager persistenceManager) {
        super(persistenceManager);
    }
}
