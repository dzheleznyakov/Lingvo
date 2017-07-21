package com.zheleznyakov.lingvo.basic;

public interface Noun extends Word {

    boolean isRegular();

    boolean isProperNoun();

    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.NOUN;
    }
}
