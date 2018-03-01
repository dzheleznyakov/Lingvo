package com.zheleznyakov.lingvo.basic.persistence.converters;

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary;
import com.zheleznyakov.lingvo.basic.persistence.ObjectPersistenceEntity;
import com.zheleznyakov.lingvo.basic.persistence.PersistenceEntity;

public class LearningDictionaryConverter implements Converter<LearningDictionary> {

    @Override
    public PersistenceEntity convert(LearningDictionary dictionary) {
        PersistenceEntity persistenceEntity = new ObjectPersistenceEntity(dictionary.getClass());
        persistenceEntity.addField("config", new ObjectPersistenceEntity(dictionary.getConfig().getClass()));
        return persistenceEntity;
    }

}
