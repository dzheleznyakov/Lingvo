package com.zheleznyakov.lingvo.basic.parts;

import com.zheleznyakov.lingvo.basic.Word;

public interface Verb extends Word {

    boolean isRegular();

    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.VERB;
    }
}
