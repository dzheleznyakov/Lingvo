package com.zheleznyakov.lingvo.basic.parts;

import com.zheleznyakov.lingvo.basic.Word;

public interface Preposition extends Word {
    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.PREPOSITION;
    }
}
