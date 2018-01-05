package com.zheleznyakov.lingvo.fx.nodes.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public abstract class AbstractControlButton extends Button {
    protected static final double RADIUS = 13;
    private static final double STROKE_WIDTH = 2;

    protected final Pane ICON;

    public AbstractControlButton() {
        ICON = getIconStub();
        fillIcon();
        setGraphic(ICON);
        setId(createId());
    }

    private Pane getIconStub() {
        Circle circle = new Circle(RADIUS, RADIUS, RADIUS);
        styleIconShapes(circle);
        return new Pane(circle);
    }

    protected static void styleIconShapes(Shape... shapes) {
        for (Shape shape : shapes) {
            shape.setStroke(Color.BLACK);
            shape.setStrokeWidth(STROKE_WIDTH);
            shape.setFill(null);
        }
    }

    protected abstract void fillIcon();


    protected abstract String createId();
}
