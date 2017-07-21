package com.zheleznyakov.lingvo.basic;

public interface Noun extends Word {

    String[] getForms();

    boolean isRegular();

    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.NOUN;
    }
}
