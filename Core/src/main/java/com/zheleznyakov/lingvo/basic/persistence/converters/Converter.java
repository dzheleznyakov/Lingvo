package com.zheleznyakov.lingvo.basic.persistence.converters;

import com.zheleznyakov.lingvo.basic.persistence.entities.PersistenceEntity;

public interface Converter<T> {
    PersistenceEntity convert(T object);
}
