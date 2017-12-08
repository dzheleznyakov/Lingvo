package com.zheleznyakov.lingvo.ui.fx.controllers;

import java.io.IOException;

import javafx.stage.Stage;

import com.zheleznyakov.lingvo.basic.Language;
import com.zheleznyakov.lingvo.ui.fx.exceptions.UiFxException;
import com.zheleznyakov.lingvo.ui.fx.nodes.panes.DictionaryPane;
import com.zheleznyakov.lingvo.util.Util;

public class MainPageController {
    private final Stage stage;

    public MainPageController(Stage stage) {
        this.stage = stage;
    }

    public void exitApp() { System.exit(1); }

    public void onLanguageChosen(Language language) {
        DictionaryPane dictionaryPane;
        try {
            dictionaryPane = new DictionaryPane(language);
        } catch (IOException | ClassNotFoundException e) {
            throw UiFxException.create(e, "Failed to create DictionaryPane for [{}]", language);
        }
//        dictionaryPane.setOnBack(this::setChooseLanguagePane);
        stage.getScene().setRoot(dictionaryPane);
        String languageForTitle = Util.capitalize(language.name().toLowerCase());
        stage.setTitle(stage.getTitle() + ": " + languageForTitle);
    }
}
