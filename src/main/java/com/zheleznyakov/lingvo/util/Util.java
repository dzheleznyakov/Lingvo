package com.zheleznyakov.lingvo.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

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

    public static double max(double... numbers) {
        validateArgument(numbers != null && numbers.length > 0, "There are not max elements in {}", numbers);
        double max = numbers[0];
        for (int i = 1; i < numbers.length; i++)
            max = max < numbers[i] ? numbers[i] : max;
        return max;
    }

    public static <T> double max(Function<T, Double> mapperToDouble, T... elements) {
        validateArgument(elements != null && elements.length > 0, "There are not max elements in {}", elements);
        double max = mapperToDouble.apply(elements[0]);
        for (int i = 0; i < elements.length; i++) {
            double value = mapperToDouble.apply(elements[i]);
            max = max < value ? value : max;
        }
        return max;
    }

}
