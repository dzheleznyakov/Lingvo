package com.zheleznyakov.lingvo.ui.fx.buttons;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class PlusButton extends AbstractPaneButton {
    private static final Node ICON;

    static {
        Circle circle = new Circle(RADIUS, RADIUS, RADIUS);
        Line verticalLine = new Line(RADIUS, RADIUS * 0.5, RADIUS, RADIUS * 1.5);
        Line horizontalLine = new Line(RADIUS * 0.5, RADIUS, RADIUS * 1.5, RADIUS);

        styleIconShapes(circle, verticalLine, horizontalLine);
        ICON = new Pane(circle, verticalLine, horizontalLine);
    }

    public PlusButton() {
        super(ICON);
    }
}
