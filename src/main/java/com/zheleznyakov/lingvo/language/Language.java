package com.zheleznyakov.lingvo.language;

public enum Language {
    ENGLISH,
    SPANISH;

    public String toLowerCase() {
        return name().toLowerCase();
    }
}
