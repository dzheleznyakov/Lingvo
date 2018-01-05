package com.zheleznyakov.lingvo.fx.exceptions;

import com.zheleznyakov.lingvo.util.Util;

public class UiFxException extends RuntimeException {
    public UiFxException(String message, Throwable cause) {
        super(message, cause);
    }

    public static UiFxException create(Throwable cause, String messagePattern, Object... messageArguments) {
        String message = Util.format(messagePattern, messageArguments);
        return new UiFxException(message, cause);
    }
}
