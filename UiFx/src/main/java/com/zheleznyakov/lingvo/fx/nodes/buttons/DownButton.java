package com.zheleznyakov.lingvo.fx.nodes.buttons;

import javafx.scene.shape.Polygon;

public class DownButton extends AbstractControlButton {
    @Override
    protected void fillIcon() {
        Polygon triangle = new Polygon(
                RADIUS, RADIUS * 1.4,
                RADIUS * 1.4, RADIUS * 0.8,
                RADIUS * 0.6, RADIUS * 0.8);
        ICON.getChildren().add(triangle);
    }

    @Override
    protected String createId() {
        return "downButton";
    }
}
