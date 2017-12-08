package com.zheleznyakov.lingvo.basic;

public interface Article extends Word {
    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.ARTICLE;
    }
}
