package com.zheleznyakov.lingvo.basic.parts;

import com.zheleznyakov.lingvo.basic.Word;

public interface Article extends Word {
    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.ARTICLE;
    }
}
