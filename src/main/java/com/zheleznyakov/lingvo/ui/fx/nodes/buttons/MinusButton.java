package com.zheleznyakov.lingvo.ui.fx.nodes.buttons;

import javafx.scene.shape.Line;

public class MinusButton extends AbstractControlButton {
    @Override
    protected void fillIcon() {
        Line horizontalLine = new Line(RADIUS * 0.5, RADIUS, RADIUS * 1.5, RADIUS);

        styleIconShapes(horizontalLine);
        ICON.getChildren().add(horizontalLine);
    }

    @Override
    protected String createId() {
        return "minusButton";
    }
}
