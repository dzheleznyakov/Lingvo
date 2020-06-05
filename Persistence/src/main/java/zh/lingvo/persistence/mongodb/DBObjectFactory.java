package zh.lingvo.persistence.mongodb;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import zh.lingvo.domain.DomainEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class DBObjectFactory {
    public DBObject convert(DomainEntity entity) throws InvocationTargetException, IllegalAccessException {
        BasicDBObject dbObject = new BasicDBObject("_id", entity.getId().getValue());
        var getters = Arrays.stream(entity.getClass().getMethods())
                .filter(method -> !Modifier.isStatic(method.getModifiers()))
                .filter(method -> !Modifier.isNative(method.getModifiers()))
                .filter(method -> method.getName().startsWith("get") && !Objects.equal(method.getName(), "getId"))
                .filter(method -> method.getParameterCount() == 0)
                .collect(ImmutableList.toImmutableList());
        for (var getter : getters) {
            String fieldName = getFieldName(getter);
            Object value = getter.invoke(entity);
            dbObject.append(fieldName, value);
        }
        return dbObject;
    }

    private String getFieldName(Method method) {
        String methodName = method.getName();
        String fieldName = methodName.substring("get".length());
        return Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
    }
}
