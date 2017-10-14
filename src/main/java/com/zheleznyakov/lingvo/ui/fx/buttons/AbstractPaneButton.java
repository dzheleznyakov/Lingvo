package com.zheleznyakov.lingvo.ui.fx.buttons;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class AbstractPaneButton extends Button {
    protected static final Node ICON;
    protected static final double RADIUS = 10;

    static {
        Circle circle = new Circle(RADIUS, RADIUS, RADIUS);
        ButtonsUtil.styleIconShapes(circle);
        ICON = new Pane(circle);
    }
}
