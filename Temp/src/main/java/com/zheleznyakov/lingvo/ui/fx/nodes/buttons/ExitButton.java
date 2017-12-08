package com.zheleznyakov.lingvo.ui.fx.nodes.buttons;

import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class ExitButton extends AbstractControlButton {
    @Override
    protected void fillIcon() {
        Rectangle rectangle = new Rectangle(RADIUS * 0, RADIUS * 0, RADIUS * 1.5, RADIUS * 2);
        double startX = RADIUS * 0.8;
        double startY = RADIUS;
        Line horizontalBar = new Line(startX, startY, RADIUS * 2, RADIUS);
        Line upperBar = new Line(startX, startY, RADIUS * 1.1, RADIUS * 0.7);
        Line bottomBar = new Line(startX, startY, RADIUS * 1.1, RADIUS * 1.3);

        styleIconShapes(rectangle, horizontalBar, upperBar, bottomBar);
        ICON.getChildren().get(0).setOpacity(0);
        ICON.getChildren().addAll(rectangle, horizontalBar, upperBar, bottomBar);
    }

    @Override
    protected String createId() {
        return "exitButton";
    }
}
