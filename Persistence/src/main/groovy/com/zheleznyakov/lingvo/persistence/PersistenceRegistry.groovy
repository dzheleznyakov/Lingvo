package com.zheleznyakov.lingvo.persistence

import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.persistence.Persistable
import com.zheleznyakov.lingvo.basic.persistence.PersistableMetadata

import java.lang.reflect.Field

class PersistenceRegistry {
    private static Map<Class<?>, Set<Field>> persistableFieldsByClass = new HashMap<>()
    private static Map<Class<?>, Boolean> hasPersistableMetadata = new HashMap<>()

    private PersistenceRegistry() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    static Set<Field> getPersistableFields(Class<?> clazz) {
        return persistableFieldsByClass.computeIfAbsent(clazz, { cl ->
            Arrays.stream(cl.declaredFields)
                    .filter { Field field -> field.getAnnotation(Persistable) != null }
                    .collect(ImmutableSet.toImmutableSet())
        })
    }

    static boolean hasPersistableMetadata(Class<?> clazz) {
        return hasPersistableMetadata.computeIfAbsent(clazz, { cl ->
            if (clazz.getAnnotation(PersistableMetadata) != null)
                return true
            for (def interfaceClass : clazz.getInterfaces()) {
                if (interfaceClass.getAnnotation(PersistableMetadata) != null)
                    return true
            }
            return false
        })
    }
}
