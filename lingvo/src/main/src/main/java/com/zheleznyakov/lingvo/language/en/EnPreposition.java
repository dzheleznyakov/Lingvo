package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Preposition;

public class EnPreposition extends EnWord implements Preposition {

    EnPreposition(String mainForm) {
        super(mainForm);
    }

    public static EnPreposition build(String mainForm) {
        return new EnPreposition(mainForm);
    }
}
