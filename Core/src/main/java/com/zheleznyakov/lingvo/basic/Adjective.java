package com.zheleznyakov.lingvo.basic;

public interface Adjective extends Word {
    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.ADJECTIVE;
    }
}
