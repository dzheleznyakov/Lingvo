package com.zheleznyakov.lingvo.basic;

public interface Verb extends Word {

    boolean isRegular();

    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.VERB;
    }
}
