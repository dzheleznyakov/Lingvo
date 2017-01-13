package com.zheleznyakov.lingvo.utils;

public enum Language {
    ENGLISH("En"),
    RUSSIAN("Ru"),
    SPANISH("Es");
    
    private final String code;

    private Language(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}