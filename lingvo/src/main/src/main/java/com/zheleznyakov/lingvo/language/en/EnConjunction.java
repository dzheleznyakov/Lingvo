package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Conjunction;

public class EnConjunction extends EnWord implements Conjunction {

    private EnConjunction(String mainForm) {
        super(mainForm);
    }

    public static EnConjunction build(String mainForm) {
        return new EnConjunction(mainForm);
    }
}
