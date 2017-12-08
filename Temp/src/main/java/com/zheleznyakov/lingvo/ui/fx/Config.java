package com.zheleznyakov.lingvo.ui.fx;


import static com.zheleznyakov.lingvo.basic.Language.ENGLISH;

import javafx.geometry.Insets;

import com.zheleznyakov.lingvo.basic.Language;


public interface Config {
    double MIN_SPACE = 5;
    Insets INSETS = new Insets(10);
    Language DEFAULT_LANGUAGE = ENGLISH;
}
