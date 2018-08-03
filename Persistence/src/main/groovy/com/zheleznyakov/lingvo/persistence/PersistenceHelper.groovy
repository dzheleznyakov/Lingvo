package com.zheleznyakov.lingvo.persistence

import com.google.common.collect.ImmutableSet
import com.google.common.reflect.ClassPath
import com.zheleznyakov.lingvo.basic.persistence.Persistable
import com.zheleznyakov.lingvo.persistence.xml.serializers.GrammaticalWordXmlSerializer
import com.zheleznyakov.lingvo.util.Util

import java.lang.reflect.Field

class PersistenceHelper {
    private static Map<Class<?>, Set<Field>> persistableFieldsByClass = new HashMap<>()
    private static Set<Class<? extends GrammaticalWordXmlSerializer>> wordSerializers = new HashSet<>()

    private PersistenceHelper() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    static Set<Field> getPersistableFields(Class<?> clazz) {
        return persistableFieldsByClass.computeIfAbsent(clazz, { cl ->
            Arrays.stream(cl.declaredFields)
                    .filter { Field field -> field.getAnnotation(Persistable) != null }
                    .collect(ImmutableSet.toImmutableSet())
        })
    }

    static void loadWordSerializers(String packageName) {
        if (Util.isBlank(packageName))
            return;

        def newLoadedWordSerializers = ClassPath.from(ClassLoader.getSystemClassLoader())
                .getTopLevelClasses(packageName)
                .stream()
                .map { classInfo -> classInfo.load() }
                .filter { cl -> GrammaticalWordXmlSerializer.isAssignableFrom(cl) }
                .collect(ImmutableSet.toImmutableSet())
        wordSerializers.addAll newLoadedWordSerializers
    }


    static Set<Class<? extends GrammaticalWordXmlSerializer>> getWordSerializers() {
        return wordSerializers
    }
}
