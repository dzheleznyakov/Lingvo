package com.zheleznyakov.lingvo.persistence

import com.google.common.collect.ImmutableSet
import com.google.common.reflect.ClassPath
import com.zheleznyakov.lingvo.basic.persistence.Persistable
import com.zheleznyakov.lingvo.basic.persistence.PersistableMetadata
import org.apache.commons.lang3.ClassUtils

import java.lang.reflect.Field

class PersistenceRegistry {
    private static Map<Class<?>, Set<Field>> persistableFieldsByClass = new HashMap<>()
    private static Map<Class<?>, Boolean> hasPersistableMetadata = new HashMap<>()

    private static Map<String, Class<?>> classSimpleNamesToClasses = new HashMap<>()
    private static Set<Class<?>> loadedClasses
    public static final String ROOT_PACKAGE = "com.zheleznyakov.lingvo"

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
            return cl.getAnnotation(PersistableMetadata) != null ||
                    ClassUtils.getAllInterfaces(cl).any { it.getAnnotation(PersistableMetadata) != null }
        })
    }

    static Class<?> getClass(String className) {
        return classSimpleNamesToClasses.computeIfAbsent(className, { name ->
            getLoadedClasses().stream()
                    .filter { clazz -> clazz.simpleName == name }
                    .findAny()
                    .orElse(null)
        })
    }

    private static Set<Class<?>> getLoadedClasses() {
        if (loadedClasses == null) {
            ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader())
            loadedClasses = classPath.getTopLevelClassesRecursive(ROOT_PACKAGE).stream()
                    .map { classInfo -> classInfo.load() }
                    .collect(ImmutableSet.toImmutableSet())
        }
        return loadedClasses
    }
}
