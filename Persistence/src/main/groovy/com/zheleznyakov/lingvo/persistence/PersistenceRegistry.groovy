package com.zheleznyakov.lingvo.persistence

import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.persistence.Persistable

import java.lang.reflect.Field

class PersistenceRegistry {
    private static Map<Class<?>, Set<Field>> persistableFieldsByClass = new HashMap<>()

    private PersistenceRegistry() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    static <E> Set<Field> getPersistableFields(Class<E> clazz) {
        println ""
        return persistableFieldsByClass.computeIfAbsent(clazz, { cl ->
            Arrays.stream(cl.declaredFields)
                    .filter { Field field -> field.getAnnotation(Persistable) != null }
                    .collect(ImmutableSet.toImmutableSet())
        })
    }
}
