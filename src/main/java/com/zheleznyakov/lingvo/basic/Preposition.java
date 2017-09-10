package com.zheleznyakov.lingvo.basic;

public interface Preposition extends Word {
    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.PREPOSITION;
    }
}
