package com.zheleznyakov.lingvo.util;

public interface Noun extends Word {

    String[] getDeclensions();

    boolean isRegular();

    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.NOUN;
    }
}
