package com.zheleznyakov.lingvo.basic.words;

public interface Language {

    String name();
    String code();
    boolean isStringLegal(String string);
}
