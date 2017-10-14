package com.zheleznyakov.lingvo.ui.fx.buttons;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class ButtonsUtil {
    private static final double STROKE_WIDTH = 2;

    public static void styleIconShapes(Shape... shapes) {
        for (Shape shape : shapes) {
            shape.setStroke(Color.BLACK);
            shape.setStrokeWidth(STROKE_WIDTH);
            shape.setFill(null);
        }
    }

}
