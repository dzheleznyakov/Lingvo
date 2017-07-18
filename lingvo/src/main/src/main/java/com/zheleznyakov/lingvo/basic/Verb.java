package com.zheleznyakov.lingvo.basic;

public interface Verb extends Word {
    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.VERB;
    }
}
