package com.zheleznyakov.lingvo.basic.dictionary;

import com.zheleznyakov.lingvo.util.Util;

public class ExerciseException extends Exception {
    public ExerciseException(String message, Object... arguments) {
        super(Util.format(message, arguments));
    }
}
