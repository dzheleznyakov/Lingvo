package com.zheleznyakov.lingvo.ui.fx.buttons;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class BackButton extends AbstractPaneButton {
    private static final Node ICON;

    static {
        Circle circle = new Circle(RADIUS, RADIUS, RADIUS);

        double arrowStartX = RADIUS * 0.5;
        double arrowStartY = RADIUS;
        Line arrowMiddle = new Line(arrowStartX, arrowStartY, RADIUS * 1.5, RADIUS);
        Line arrowTop = new Line(arrowStartX, arrowStartY, RADIUS * 0.8, RADIUS * 0.7);
        Line arrowBottom = new Line(arrowStartX, arrowStartY, RADIUS * 0.8, RADIUS * 1.3);

        styleIconShapes(circle, arrowMiddle, arrowTop, arrowBottom);
        ICON = new Pane(circle, arrowMiddle, arrowTop, arrowBottom);
    }

    public BackButton() {
        super(ICON);
    }
}
