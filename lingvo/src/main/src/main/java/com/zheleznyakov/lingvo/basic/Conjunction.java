package com.zheleznyakov.lingvo.basic;

public interface Conjunction extends Word{
    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.CONJUNCTION;
    }
}
