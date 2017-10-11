package com.zheleznyakov.lingvo.ui.fx;

import static com.zheleznyakov.lingvo.ui.fx.panes.Layout.MIN_SPACE;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import com.zheleznyakov.lingvo.ui.fx.panes.ChooseLanguagePane;
import com.zheleznyakov.lingvo.ui.fx.panes.LoadDictionaryPane;

public class UiFxMain extends Application {

    private Scene scene;
    private ChooseLanguagePane chooseLanguagePane;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        chooseLanguagePane = new ChooseLanguagePane();

        scene = new Scene(chooseLanguagePane, 300, 300);
        this.primaryStage = primaryStage;
        primaryStage.setTitle("ZhLingvo");
        primaryStage.setScene(scene);
        primaryStage.show();

        configure(chooseLanguagePane);

        chooseLanguagePane.setOnChosen(this::setLoadDictionaryPane);
    }

    private void configure(Pane pane) {
        double height = pane.getHeight() + 2 * MIN_SPACE;
        double width = pane.getWidth() + 2 * MIN_SPACE;
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
    }

    private void setLoadDictionaryPane(ActionEvent event) {
        LoadDictionaryPane loadDictionaryPane = new LoadDictionaryPane(chooseLanguagePane.getLanguage());
        scene.setRoot(loadDictionaryPane);
        configure(chooseLanguagePane);
    }
}
