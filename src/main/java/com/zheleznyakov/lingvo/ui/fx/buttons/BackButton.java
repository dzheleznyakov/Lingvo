package com.zheleznyakov.lingvo.ui.fx.buttons;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class BackButton extends Button {
    private static final Node ICON;
    private static final double RADIUS = 10;
    private static final double STROKE_WIDTH = 2;

    static {
        Circle circle = new Circle(RADIUS, RADIUS, RADIUS);

        double arrowStartX = RADIUS * 0.5;
        double arrowStartY = RADIUS;
        Line arrowMiddle = new Line(arrowStartX, arrowStartY, RADIUS * 1.5, RADIUS);
        Line arrowTop = new Line(arrowStartX, arrowStartY, RADIUS * 0.8, RADIUS * 0.7);
        Line arrowBottom = new Line(arrowStartX, arrowStartY, RADIUS * 0.8, RADIUS * 1.3);

        ButtonsUtil.styleIconShapes(circle, arrowMiddle, arrowTop, arrowBottom);
        ICON = new Pane(circle, arrowMiddle, arrowTop, arrowBottom);
    }

    public BackButton() {
        super(null, ICON);
    }
}
