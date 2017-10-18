package com.zheleznyakov.lingvo.ui.fx.panes;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;

public class NewWordPane extends BorderPane {
//    ImmutableList<Class<? extends  Word>>

    public NewWordPane() {
        Rectangle rectangle = new Rectangle(100, 100);
        setCenter(rectangle);
    }
}
