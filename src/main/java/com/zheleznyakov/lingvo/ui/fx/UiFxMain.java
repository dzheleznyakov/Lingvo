package com.zheleznyakov.lingvo.ui.fx;

import static com.zheleznyakov.lingvo.ui.fx.panes.Layout.MIN_SPACE;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import com.zheleznyakov.lingvo.ui.fx.panes.ChooseLanguagePane;
import com.zheleznyakov.lingvo.ui.fx.panes.LoadDictionaryPane;
import com.zheleznyakov.lingvo.util.Util;

public class UiFxMain extends Application {

    private static final String TITLE = "ZhLingvo";

    private Scene scene;
    private ChooseLanguagePane chooseLanguagePane;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        chooseLanguagePane = new ChooseLanguagePane();

        scene = new Scene(chooseLanguagePane, 350, 350);
        this.primaryStage = primaryStage;
        primaryStage.setTitle(TITLE);
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
        loadDictionaryPane.setOnBack(this::setChooseLanguagePane);
        scene.setRoot(loadDictionaryPane);
        String languageForTitle = Util.capitalize(chooseLanguagePane.getLanguage().name().toLowerCase());
        primaryStage.setTitle(TITLE + ": " + languageForTitle);
    }

    private void setChooseLanguagePane(ActionEvent event) {
        scene.setRoot(chooseLanguagePane);
        primaryStage.setTitle(TITLE);
    }
}
