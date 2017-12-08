package com.zheleznyakov.lingvo.basic.parts;

import com.zheleznyakov.lingvo.basic.Word;

public interface Noun extends Word {

    boolean isRegular();

    boolean isProperNoun();

    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.NOUN;
    }
}
