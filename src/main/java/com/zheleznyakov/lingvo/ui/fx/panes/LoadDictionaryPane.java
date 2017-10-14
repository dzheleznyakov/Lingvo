package com.zheleznyakov.lingvo.ui.fx.panes;

import static com.zheleznyakov.lingvo.dictionary.persistence.PersistenceManager.DIC_EXTENSION;
import static com.zheleznyakov.lingvo.ui.fx.Config.INSETS;
import static com.zheleznyakov.lingvo.ui.fx.Config.MIN_SPACE;
import static com.zheleznyakov.lingvo.ui.fx.Config.ROOT_PATH;
import static com.zheleznyakov.lingvo.util.Util.confirmExpression;
import static com.zheleznyakov.lingvo.util.Util.max;

import java.io.File;
import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.zheleznyakov.lingvo.dictionary.Dictionary;
import com.zheleznyakov.lingvo.dictionary.persistence.BasicPersistenceManager;
import com.zheleznyakov.lingvo.dictionary.persistence.PersistenceManager;
import com.zheleznyakov.lingvo.dictionary.persistence.PersistenceUtil;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.ui.fx.buttons.BackButton;
import com.zheleznyakov.lingvo.util.UncheckedRunnable;

public class LoadDictionaryPane extends BorderPane {

    private static final double BUTTON_MIN_WIDTH = 35;

    private final String path;
    private final File dir;
    private final Language language;

    private final Label info;
    private final ListView<Node> words;
    private final Button backButton;

    private final Button up;
    private final Button down;
    private final Button add;
    private final Button delete;

    public LoadDictionaryPane(Language language) throws IOException {
        path = ROOT_PATH + language.name().toLowerCase();
        dir = new File(path);
        this.language = language;

        info = createInfoLabel();
        words = createWordList();
        backButton = new BackButton();

        up = new Button("U");
        add = new Button("+");
        delete = new Button("-");
        down = new Button("D");

        ensureDictionaryForThisLanguage();
        equalizeButtons();
        setUp();
    }

    private void ensureDictionaryForThisLanguage() throws IOException {
        if (!dir.exists())
            dir.mkdir();
        String pathToDictionary = ROOT_PATH + "/" + language.toLowerCase();
        File dictionaryFile = new File(pathToDictionary + "." + DIC_EXTENSION);
        if (!dictionaryFile.exists())
            PersistenceUtil.get().persist(new Dictionary(language), pathToDictionary + "/" + language.toLowerCase());
    }

    @NotNull
    private Button createDeleteButton() {
        return new Button("Delete");
    }

    @NotNull
    private Button createLoadButton() {
        return new Button("Load");
    }

    @NotNull
    private Button createNewButton() {
        Button button = new Button("New");
        button.setOnAction(this::onPressingNewButton);
        return button;
    }

    private void onPressingNewButton(ActionEvent event) {
        TextField textField = new TextField();
        textField.setPromptText("Enter dictionary name");
        words.getItems().add(textField);
        textField.requestFocus();
        textField.setOnAction(this::createNewLearningDictionary);
    }

    private void createNewLearningDictionary(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        String dictionaryName = textField.getText();
        UncheckedRunnable<IOException> crateNewFile = () -> {
            // TODO: this should be a learning dictionary
            Dictionary dictionary = new Dictionary(language);
            PersistenceManager persistenceManager = new BasicPersistenceManager();
            persistenceManager.persist(dictionary, path + "/" + dictionaryName);
            Label createdDictionary = new Label(dictionaryName);
            int indexInTheListOfDictionaries = words.getItems().indexOf(textField);
            words.getItems().set(indexInTheListOfDictionaries, createdDictionary);
        };
        try {
            confirmExpression(dictionaryName.matches("[0-9A-Za-z\\-_]+"), crateNewFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private Label createInfoLabel() {
        return new Label("Choose word:");
    }

    @NotNull
    private ListView<Node> createWordList() {
        ListView<Node> wordList = new ListView<>();
        return wordList;
    }

    private void setUp() {
        BorderPane.setAlignment(info, Pos.BOTTOM_CENTER);
        setTop(info);

        VBox buttonBox = new VBox(add, up, down, delete);
        buttonBox.setSpacing(MIN_SPACE);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        HBox contentAndControlBox = new HBox(MIN_SPACE);
        contentAndControlBox.getChildren().addAll(buttonBox, words);
        contentAndControlBox.setAlignment(Pos.TOP_CENTER);
        BorderPane.setAlignment(contentAndControlBox, Pos.TOP_CENTER);
        setCenter(contentAndControlBox);

        BorderPane.setAlignment(backButton, Pos.BOTTOM_LEFT);
        setLeft(backButton);

        setPadding(INSETS);
    }

    private void equalizeButtons() {
        Button[] buttons = {up, down, add, delete};
        double maxHeight = max(Button::getHeight, buttons);
        double maxWidth = max(Button::getWidth, buttons);
        for (Button button : buttons) {
            button.setMinWidth(BUTTON_MIN_WIDTH);
            button.setPrefSize(maxWidth, maxHeight);
        }
    }

    public void setOnBack(EventHandler<ActionEvent> handler) {
        backButton.setOnAction(handler);
    }

}
