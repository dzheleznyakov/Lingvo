package com.zheleznyakov.lingvo.fx.nodes.buttons;

import javafx.scene.shape.Line;

public class ForwardButton extends AbstractControlButton {
    @Override
    protected void fillIcon() {
        double arrowStartX = RADIUS * 1.5;
        double arrowStartY = RADIUS;
        Line arrowMiddle = new Line(arrowStartX, arrowStartY, RADIUS * 0.5, RADIUS);
        Line arrowTop = new Line(arrowStartX, arrowStartY, RADIUS * 1.2, RADIUS * 0.7);
        Line arrowBottom = new Line(arrowStartX, arrowStartY, RADIUS * 1.2, RADIUS * 1.3);

        styleIconShapes(arrowMiddle, arrowTop, arrowBottom);
        ICON.getChildren().addAll(arrowMiddle, arrowTop, arrowBottom);


    }

    @Override
    protected String createId() {
        return "forwardButton";
    }
}
