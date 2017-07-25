package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Adverb;

public class EnAdverb extends EnWord implements Adverb {

    private EnAdverb(String mainForm) {
        super(mainForm);
    }

    public static EnAdverb build(String mainForm) {
        return new EnAdverb(mainForm);
    }
}
