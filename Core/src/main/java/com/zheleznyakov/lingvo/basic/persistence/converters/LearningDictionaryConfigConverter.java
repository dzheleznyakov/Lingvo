package com.zheleznyakov.lingvo.basic.persistence.converters;

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig;
import com.zheleznyakov.lingvo.basic.persistence.*;
import com.zheleznyakov.lingvo.basic.persistence.entities.BooleanPersistenceEntity;
import com.zheleznyakov.lingvo.basic.persistence.entities.IntegerPersistenceEntity;
import com.zheleznyakov.lingvo.basic.persistence.entities.ObjectPersistenceEntity;
import com.zheleznyakov.lingvo.basic.persistence.entities.PersistenceEntity;

public class LearningDictionaryConfigConverter implements Converter<LearningDictionaryConfig> {

    @Override
    public PersistenceEntity convert(LearningDictionaryConfig config) {
        ObjectPersistenceEntity persistenceEntity = new ObjectPersistenceEntity(config.getClass());
        persistenceEntity.addField("mode", new StringPersistenceEntity(config.getMode().name()));
        persistenceEntity.addField("maxLearnCount", new IntegerPersistenceEntity(config.getMaxLearnCount()));
        persistenceEntity.addField("strict", new BooleanPersistenceEntity(config.isStrict()));
        return persistenceEntity;
    }

}
