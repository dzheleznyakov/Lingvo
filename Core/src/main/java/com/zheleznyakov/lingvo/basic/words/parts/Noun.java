package com.zheleznyakov.lingvo.basic.words.parts;

import com.zheleznyakov.lingvo.basic.words.GrammaticalWord;

public interface Noun extends GrammaticalWord {

    boolean isRegular();

    boolean isProperNoun();

    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.NOUN;
    }
}
