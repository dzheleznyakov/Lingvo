package com.zheleznyakov.lingvo.endpoints;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.zheleznyakov.lingvo.basic.PartOfSpeech;

public class EnglishEndpoint {
    public List<PartOfSpeech> getPartsOfSpeeches() {
        return ImmutableList.copyOf(PartOfSpeech.values());
    }
}
