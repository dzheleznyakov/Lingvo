package com.zheleznyakov.lingvo.basic.parts;

public enum PartOfSpeech {
    NOUN("n"),
    VERB("v"),
    ADJECTIVE("adj"),
    ADVERB("adv"),
    PREPOSITION("prep"),
    CONJUNCTION("conj"),
    ARTICLE("art");

    public final String brief;

    PartOfSpeech(String brief) {
        this.brief = brief;
    }
}
