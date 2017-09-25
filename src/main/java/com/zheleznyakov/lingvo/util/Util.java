package com.zheleznyakov.lingvo.util;

import java.util.Collection;
import java.util.Iterator;

public class Util {

    private Util() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    public static void validateArgument(boolean expression, String messagePattern, Object... arguments) {
        if (!expression)
            throw new IllegalArgumentException(format(messagePattern, arguments));
    }

    public static void validateState(boolean expression, String messagePattern, Object... arguments) {
        if (!expression)
            throw new IllegalStateException(format(messagePattern, arguments));
    }

    public static String format(String messagePattern, Object... arguments) {
        if (messagePattern == null)
            return null;

        String placeholder = "{}";
        StringBuilder formattedMessage = new StringBuilder();
        int placeholderIndex = messagePattern.indexOf(placeholder);
        int nextAppendIndex = 0;
        int argumentCount = 0;

        while (placeholderIndex >= 0) {
            Object argument = arguments.length > argumentCount ? arguments[argumentCount] : "";
            String nextPartToAppend = messagePattern.substring(nextAppendIndex, placeholderIndex);
            formattedMessage
                    .append(nextPartToAppend)
                    .append(argument);

            nextAppendIndex = Math.max(nextAppendIndex, placeholderIndex + 2);
            placeholderIndex = messagePattern.indexOf(placeholder, placeholderIndex + 1);
            argumentCount++;
        }

        String tail = messagePattern.substring(nextAppendIndex);
        return formattedMessage
                .append(tail)
                .toString();
    }

    public static <T> T getRandom(Collection<T> objects) {
        if (objects == null || objects.isEmpty())
            return null;
        int index = (int) (Math.random() * objects.size());
        int currentIndex = 0;
        Iterator<T> iterator = objects.iterator();
        while (currentIndex++ < index)
            iterator.next();
        return iterator.next();
    }

}
