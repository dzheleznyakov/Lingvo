package com.zheleznyakov.lingvo.basic.persistence.converters;

import com.zheleznyakov.lingvo.basic.dictionary.Record;
import com.zheleznyakov.lingvo.basic.persistence.entities.ObjectPersistenceEntity;
import com.zheleznyakov.lingvo.basic.persistence.entities.PersistenceEntity;

public class RecordConverter implements Converter<Record> {

    @Override
    public PersistenceEntity convert(Record object) {
        ObjectPersistenceEntity persistenceEntity = new ObjectPersistenceEntity(Record.class);

        return persistenceEntity;
    }

}
