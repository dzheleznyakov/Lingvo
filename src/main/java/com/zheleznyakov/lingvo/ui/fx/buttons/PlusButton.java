package com.zheleznyakov.lingvo.ui.fx.buttons;

import javafx.scene.shape.Line;

public class PlusButton extends AbstractControlButton {
    @Override
    protected void fillIcon() {
        Line verticalLine = new Line(RADIUS, RADIUS * 0.5, RADIUS, RADIUS * 1.5);
        Line horizontalLine = new Line(RADIUS * 0.5, RADIUS, RADIUS * 1.5, RADIUS);

        styleIconShapes(verticalLine, horizontalLine);
        ICON.getChildren().addAll(verticalLine, horizontalLine);
    }

    @Override
    protected String createId() {
        return "plusButton";
    }
}
