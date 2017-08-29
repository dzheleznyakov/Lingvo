package com.zheleznyakov.lingvo.util;

public class Precondition {

    private Precondition() throws IllegalAccessException {
        throw new IllegalAccessException("This class is a static helper; it is not supposed to be instantiated");
    }

    public static void validateArgument(boolean expression, String messagePattern, Object... arguments) {
        if (!expression)
            throw new IllegalArgumentException(format(messagePattern, arguments));
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

        String lastPartToAppend = messagePattern.substring(nextAppendIndex);
        return formattedMessage
                .append(lastPartToAppend)
                .toString();
    }

}
