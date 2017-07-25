package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Adjective;

public class EnAdjective extends EnWord implements Adjective {

    private EnAdjective(String mainForm) {
        super(mainForm);
    }

    public static EnAdjective build(String adjString) {
        return new EnAdjective(adjString);
    }
}
