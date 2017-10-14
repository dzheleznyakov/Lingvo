package com.zheleznyakov.lingvo.ui.fx.buttons;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class MinusButton extends AbstractPaneButton {
    private static final Node ICON;

    static {
        Circle circle = new Circle(RADIUS, RADIUS, RADIUS);
        Line horizontalLine = new Line(RADIUS * 0.5, RADIUS, RADIUS * 1.5, RADIUS);

        styleIconShapes(circle, horizontalLine);
        ICON = new Pane(circle, horizontalLine);
    }

    public MinusButton() {
        super(ICON);
    }
}
