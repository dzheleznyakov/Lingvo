package com.zheleznyakov.lingvo.ui.fx.panes;

import static com.zheleznyakov.lingvo.dictionary.persistence.PersistenceManager.DIC_EXTENSION;
import static com.zheleznyakov.lingvo.ui.fx.Config.INSETS;
import static com.zheleznyakov.lingvo.ui.fx.Config.MIN_SPACE;
import static com.zheleznyakov.lingvo.ui.fx.Config.ROOT_PATH;

import java.io.File;
import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.zheleznyakov.lingvo.dictionary.Dictionary;
import com.zheleznyakov.lingvo.dictionary.persistence.PersistenceUtil;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.ui.fx.buttons.BackButton;
import com.zheleznyakov.lingvo.ui.fx.buttons.DownButton;
import com.zheleznyakov.lingvo.ui.fx.buttons.MinusButton;
import com.zheleznyakov.lingvo.ui.fx.buttons.PlusButton;
import com.zheleznyakov.lingvo.ui.fx.buttons.UpButton;
import com.zheleznyakov.lingvo.util.Util;

public class LoadDictionaryPane extends BorderPane {

    private final String path;
    private final File dir;
    private final Language language;
    private Dictionary dictionary;

    private final Label info;
    private final ListView<Node> words;
    private final Button backButton;

    private final Button up;
    private final Button down;
    private final Button add;
    private final Button delete;

    public LoadDictionaryPane(Language language) throws IOException, ClassNotFoundException {
        path = ROOT_PATH + language.name().toLowerCase();
        dir = new File(path);
        this.language = language;
        ensureRootFolderForThisLanguaпe();
        ensureDictionaryForThisLanguage();

        info = createInfoLabel();
        words = createWordList();
        backButton = new BackButton();

        up = new UpButton();
        add = new PlusButton();
        delete = new MinusButton();
        down = new DownButton();

        setUp();
    }

    private void ensureRootFolderForThisLanguaпe() {
        if (!dir.exists())
            dir.mkdir();
    }

    private void ensureDictionaryForThisLanguage() throws IOException, ClassNotFoundException {
        String pathToDictionary = ROOT_PATH + language.toLowerCase();
        String fileName = language.toLowerCase() + DIC_EXTENSION;
        File dictionaryFile = new File(pathToDictionary + fileName);
        if (!dictionaryFile.exists()) {
            createAndPersistDictionary(pathToDictionary);
        }
        else {
            dictionary = PersistenceUtil.get().load(Dictionary.class, pathToDictionary , fileName);
        }
    }

    private void createAndPersistDictionary(String pathToDictionary) throws IOException {
        dictionary = new Dictionary(language);
        dictionary.setTitle(language.toLowerCase());
        PersistenceUtil.get().persist(dictionary, pathToDictionary);
    }

    @NotNull
    private Label createInfoLabel() {
        return new Label("Choose word:");
    }

    @NotNull
    private ListView<Node> createWordList() {
        ListView<Node> wordList = new ListView<>();
        dictionary.getWords().forEach(word -> {
            Label wordLabel = new Label(word.getMainForm());
            wordList.getItems().add(wordLabel);
        });
        return wordList;
    }

    private void setUp() {
        BorderPane.setAlignment(info, Pos.BOTTOM_CENTER);
        setTop(info);

        VBox buttonBox = new VBox(add, up, down, delete);
        buttonBox.setSpacing(MIN_SPACE);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        setControlButtonsOnAction();

        HBox contentAndControlBox = new HBox(MIN_SPACE);
        contentAndControlBox.getChildren().addAll(buttonBox, words);
        contentAndControlBox.setAlignment(Pos.TOP_CENTER);
        BorderPane.setAlignment(contentAndControlBox, Pos.TOP_CENTER);
        setCenter(contentAndControlBox);

        BorderPane.setAlignment(backButton, Pos.BOTTOM_LEFT);
        setLeft(backButton);

        setPadding(INSETS);
    }

    private void setControlButtonsOnAction() {
        add.setOnAction(event -> {
            Pane newWordPane = new NewWordPane();

            Scene scene = new Scene(newWordPane, 350, 350);
            Stage stage = new Stage();
            stage.setTitle("ZhLingvo: " + Util.capitalize(language.toLowerCase()) + " - New Word");
            stage.setScene(scene);
            stage.show();
        });
    }

    public void setOnBack(EventHandler<ActionEvent> handler) {
        backButton.setOnAction(handler);
    }

}
