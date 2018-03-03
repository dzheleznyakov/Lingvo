package com.zheleznyakov.lingvo.basic.persistence.entities;

import com.zheleznyakov.lingvo.basic.persistence.converters.ConvertingWorkshop;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ListPersistenceEntity implements PersistenceEntity {
    private final List<PersistenceEntity> values = new LinkedList<>();

    public <E> ListPersistenceEntity(Collection<E> objects) {
        objects.forEach(object -> values.add(ConvertingWorkshop.convert(object)));
    }

    public List<PersistenceEntity> getValues() {
        return values;
    }

    public int size() {
        return values.size();
    }

}
