package com.zheleznyakov.lingvo.endpoints;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;

public class EnglishEndpoint {
    public PartOfSpeech[] getPartsOfSpeeches() {
        return PartOfSpeech.values();
    }
}
