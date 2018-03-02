package com.zheleznyakov.lingvo.basic.persistence.converters;

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary;
import com.zheleznyakov.lingvo.basic.persistence.entities.ObjectPersistenceEntity;
import com.zheleznyakov.lingvo.basic.persistence.entities.PersistenceEntity;

public class LearningDictionaryConverter implements Converter<LearningDictionary> {

    @Override
    public PersistenceEntity convert(LearningDictionary dictionary) {
        ObjectPersistenceEntity persistenceEntity = new ObjectPersistenceEntity(dictionary.getClass());
        persistenceEntity.addField("language", ConvertingWorkshop.convert(dictionary.getLanguage()));
        persistenceEntity.addField("config", ConvertingWorkshop.convert(dictionary.getConfig()));
        persistenceEntity.addField("records", ConvertingWorkshop.convert(dictionary.getRecords()));
        return persistenceEntity;
    }

}
