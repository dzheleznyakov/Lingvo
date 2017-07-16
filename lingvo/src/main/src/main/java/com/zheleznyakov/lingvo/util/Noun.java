package com.zheleznyakov.lingvo.util;

public interface Noun extends Word {

    String[] getDeclensions();

    ChangeType getChangeType();

    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.NOUN;
    }
}
