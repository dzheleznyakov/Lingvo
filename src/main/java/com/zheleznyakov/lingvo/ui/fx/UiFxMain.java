package com.zheleznyakov.lingvo.ui.fx;

import static com.zheleznyakov.lingvo.ui.fx.Config.DEFAULT_LANGUAGE;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.ui.fx.panes.ChoosePane;
import com.zheleznyakov.lingvo.ui.fx.panes.DictionaryPane;
import com.zheleznyakov.lingvo.util.Util;

public class UiFxMain extends Application {

    private static final String TITLE = "ZhLingvo";

    private Scene scene;
    private ChoosePane<Language> chooseLanguagePane;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        chooseLanguagePane = new ChoosePane<>("Choose language:", Language.values(), DEFAULT_LANGUAGE);

        scene = new Scene(chooseLanguagePane, 400, 350);
        this.primaryStage = primaryStage;
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();

        chooseLanguagePane.setOnForward(this::setLoadDictionaryPane);
        chooseLanguagePane.setOnExit(event -> System.exit(1));
    }

    private void setLoadDictionaryPane(ActionEvent event) {
        DictionaryPane dictionaryPane;
        Language language = chooseLanguagePane.getChosen();
        try {
            dictionaryPane = new DictionaryPane(language);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(Util.format("Failed to create DictionaryPane for [{}]", language));
            return;
        }
        dictionaryPane.setOnBack(this::setChooseLanguagePane);
        scene.setRoot(dictionaryPane);
        String languageForTitle = Util.capitalize(language.name().toLowerCase());
        primaryStage.setTitle(TITLE + ": " + languageForTitle);
    }

    private void setChooseLanguagePane(ActionEvent event) {
        scene.setRoot(chooseLanguagePane);
        primaryStage.setTitle(TITLE);
    }
}
