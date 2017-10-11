package com.zheleznyakov.lingvo.ui.fx;

import static com.zheleznyakov.lingvo.ui.fx.panes.Layout.MIN_SPACE;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import com.zheleznyakov.lingvo.ui.fx.panes.ChooseLanguagePane;

public class UiFxMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new ChooseLanguagePane();

        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setTitle("ZhLingvo");
        primaryStage.setScene(scene);
        primaryStage.show();

        configure(primaryStage, pane);
    }

    private static void configure(Stage stage, Pane pane) {
        double height = pane.getHeight() + 2 * MIN_SPACE;
        double width = pane.getWidth() + 2 * MIN_SPACE;
        stage.setMinHeight(height);
        stage.setMinWidth(width);
    }
}
