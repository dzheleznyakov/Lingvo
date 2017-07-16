package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.util.Word;

public interface EnWord extends Word {

    default Language getLanguage() {
        return Language.ENGLISH;
    }
}
