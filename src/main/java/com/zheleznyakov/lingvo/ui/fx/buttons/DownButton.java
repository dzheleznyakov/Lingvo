package com.zheleznyakov.lingvo.ui.fx.buttons;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class DownButton extends AbstractPaneButton {
    private static final Node ICON;

    static {
        Circle circle = new Circle(RADIUS, RADIUS, RADIUS);
        Polygon triangle = new Polygon(
                RADIUS, RADIUS * 1.4,
                RADIUS * 1.4, RADIUS * 0.8,
                RADIUS * 0.6, RADIUS * 0.8);

        styleIconShapes(circle, triangle);
        triangle.setFill(Color.BLACK);
        ICON = new Pane(circle, triangle);
    }

    public DownButton() {
        super(ICON);
    }
}