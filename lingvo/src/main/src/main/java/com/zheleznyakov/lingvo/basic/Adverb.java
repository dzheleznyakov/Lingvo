package com.zheleznyakov.lingvo.basic;

public interface Adverb extends Word {

    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.ADVERB;
    }
}
