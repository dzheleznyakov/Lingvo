package com.zheleznyakov.lingvo.basic.words.parts;

import com.zheleznyakov.lingvo.basic.words.GrammaticalWord;

public interface Verb extends GrammaticalWord {

    boolean isRegular();

    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.VERB;
    }
}
