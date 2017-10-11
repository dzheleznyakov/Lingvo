package com.zheleznyakov.lingvo.ui.fx.panes;

import static com.zheleznyakov.lingvo.language.Language.ENGLISH;

import javafx.geometry.Insets;

import com.zheleznyakov.lingvo.language.Language;

public interface Layout {
    double MIN_SPACE = 5;
    Insets INSETS = new Insets(10);
    Language DEFAULT_LANGUAGE = ENGLISH;
}
