package com.zheleznyakov.lingvo.basic.words.parts;

import com.zheleznyakov.lingvo.basic.words.GrammaticalWord;

public interface Adjective extends GrammaticalWord {
    @Override
    default PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.ADJECTIVE;
    }
}
