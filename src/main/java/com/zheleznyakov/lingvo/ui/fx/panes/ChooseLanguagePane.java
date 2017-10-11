package com.zheleznyakov.lingvo.ui.fx.panes;

import static com.zheleznyakov.lingvo.ui.fx.panes.Layout.DEFAULT_LANGUAGE;
import static com.zheleznyakov.lingvo.ui.fx.panes.Layout.INSETS;
import static com.zheleznyakov.lingvo.ui.fx.panes.Layout.MIN_SPACE;

import org.jetbrains.annotations.NotNull;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.zheleznyakov.lingvo.language.Language;

public class ChooseLanguagePane extends VBox {
    private final Label info;
    private final ComboBox<Language> languages;
    private final Button confirmButton;

    public ChooseLanguagePane() {
        super(MIN_SPACE);
        info = createInfoLabel();
        languages = createLanguageBox();
        confirmButton = createConfirmButton();
        setUp();
    }

    @NotNull
    private Label createInfoLabel() {
        return new Label("Choose language:");
    }

    @NotNull
    private ComboBox<Language> createLanguageBox() {
        ComboBox<Language> languages = new ComboBox<>();
        for (Language language : Language.values())
            languages.getItems().add(language);
        languages.setValue(DEFAULT_LANGUAGE);
        return languages;
    }

    @NotNull
    private Button createConfirmButton() {
        return new Button("Ok");
    }

    private void setUp() {
        HBox controlBox = new HBox(MIN_SPACE);
        controlBox.getChildren().addAll(languages, confirmButton);
        controlBox.setAlignment(Pos.CENTER);
        getChildren().addAll(info, controlBox);
        setPadding(INSETS);
        setAlignment(Pos.CENTER);
    }

    public Language getLanguage() {
        return languages.getValue();
    }

    public void setOnChosen(javafx.event.EventHandler<ActionEvent> event) {
        confirmButton.setOnAction(event);
    }

}
