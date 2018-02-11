package com.zheleznyakov.lingvo.en;

import com.zheleznyakov.lingvo.basic.parts.Noun;

public class EnNoun extends EnWord implements Noun {
    private EnNoun(String mainForm) {
        super(mainForm);
    }

    public static EnNoun build(String mainForm) {
        return new EnNoun(mainForm);
    }

    @Override
    public boolean isRegular() {
        return true;
    }

    @Override
    public boolean isProperNoun() {
        return false;
    }
}
