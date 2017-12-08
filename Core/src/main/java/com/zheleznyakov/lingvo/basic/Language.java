package com.zheleznyakov.lingvo.basic;

public enum Language {
    ENGLISH,
    SPANISH;

    public String toLowerCase() {
        return name().toLowerCase();
    }
}
