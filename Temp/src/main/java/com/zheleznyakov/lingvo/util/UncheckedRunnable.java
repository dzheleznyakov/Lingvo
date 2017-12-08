package com.zheleznyakov.lingvo.util;

@FunctionalInterface
public interface UncheckedRunnable<X extends Exception> {
    void run() throws X;
}
