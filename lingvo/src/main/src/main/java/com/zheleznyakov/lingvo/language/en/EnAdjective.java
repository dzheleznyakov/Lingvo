package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Adjective;

public class EnAdjective extends EnWord implements Adjective {

    public EnAdjective(String mainForm) {
        super(mainForm);
    }

    @Override
    public String[] getForms() {
        return new String[]{mainForm};
    }
}
