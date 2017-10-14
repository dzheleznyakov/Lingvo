package com.zheleznyakov.lingvo.ui.fx.buttons;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class BackButton extends Button {
    private static final Node ICON;
    private static final double RADIUS = 10;
    private static final double STROKE_WIDTH = 2;

    static {
        Circle circle = new Circle(RADIUS, RADIUS, RADIUS);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(STROKE_WIDTH);
        circle.setFill(null);

        double arrowStartX = RADIUS * 0.5;
        double arrowStartY = RADIUS;
        Line arrowMiddle = new Line(arrowStartX, arrowStartY, RADIUS * 1.5, RADIUS);
        arrowMiddle.setStrokeWidth(STROKE_WIDTH);

        Line arrowTop = new Line(arrowStartX, arrowStartY, RADIUS * 0.8, RADIUS * 0.7);
        arrowTop.setStrokeWidth(STROKE_WIDTH);

        Line arrowBottom = new Line(arrowStartX, arrowStartY, RADIUS * 0.8, RADIUS * 1.3);
        arrowBottom.setStrokeWidth(STROKE_WIDTH);

        ICON = new Pane(circle, arrowMiddle, arrowTop, arrowBottom);
    }

    public BackButton() {
        super(null, ICON);
    }
}
