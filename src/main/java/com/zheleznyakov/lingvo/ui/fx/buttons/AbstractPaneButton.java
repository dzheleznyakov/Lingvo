package com.zheleznyakov.lingvo.ui.fx.buttons;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class AbstractPaneButton extends Button {
    protected static final double RADIUS = 10;
    private static final double STROKE_WIDTH = 2;

    public AbstractPaneButton(Node graphics) {
        super(null, graphics);
    }

    protected static void styleIconShapes(Shape... shapes) {
        for (Shape shape : shapes) {
            shape.setStroke(Color.BLACK);
            shape.setStrokeWidth(STROKE_WIDTH);
            shape.setFill(null);
        }
    }
}
