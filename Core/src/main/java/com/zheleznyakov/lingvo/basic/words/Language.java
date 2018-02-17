package com.zheleznyakov.lingvo.basic.words;

public abstract class Language {

    public abstract String name();
    public abstract String code();
    public abstract boolean isStringLegal(String string);

    @Override
    public String toString() {
        return name() + " [" + code() + "]";
    }
}
