package com.zheleznyakov.lingvo.basic;

public interface Noun extends Word {

    String[] getDeclensions();

    boolean isRegular();

    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.NOUN;
    }
}
