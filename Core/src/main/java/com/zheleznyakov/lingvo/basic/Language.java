package com.zheleznyakov.lingvo.basic;

public interface Language {

    String name();
    String code();
    boolean isStringLegal(String string);
}
