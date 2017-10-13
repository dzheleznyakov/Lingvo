package com.zheleznyakov.lingvo.ui.fx.panes;

import static com.zheleznyakov.lingvo.ui.fx.panes.Layout.INSETS;
import static com.zheleznyakov.lingvo.ui.fx.panes.Layout.MIN_SPACE;
import static com.zheleznyakov.lingvo.util.Util.confirmExpression;
import static com.zheleznyakov.lingvo.util.Util.max;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.jetbrains.annotations.NotNull;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import com.zheleznyakov.lingvo.dictionary.Dictionary;
import com.zheleznyakov.lingvo.dictionary.persistence.BasicPersistenceManager;
import com.zheleznyakov.lingvo.dictionary.persistence.PersistenceManager;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.util.UncheckedRunnable;

public class LoadDictionaryPane extends BorderPane {

    private static final double BUTTON_MIN_WIDTH = 75;

    private final String path;
    private final File dir;
    private final Language language;

    private final Label info;
    private final Button loadButton;
    private final Button newButton;
    private final Button deleteButton;
    private final ListView<Node> dictionaries;
    private final Button backButton;

    public LoadDictionaryPane(Language language)  {
        path = "src/main/resources/" + language.name().toLowerCase();
        dir = new File(path);
        this.language = language;
        dictionaries = createDictionariesList();
        info = createInfoLabel();
        loadButton = new Button("Load");
        newButton = createNewButton();
        deleteButton = new Button("Delete");
        backButton = getBackButton();

        equalizeButtons();
        setUp();
    }

    @NotNull
    private Button getBackButton() {
        URI fileUri = new File("src/main/resources/images/back.png").toURI();
        Image image = new Image(fileUri.toString());
        ImageView buttonGraphic = new ImageView(image);
        buttonGraphic.setFitHeight(20);
        buttonGraphic.setFitWidth(20);
        return new Button(null, buttonGraphic);
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
        dictionaries.getItems().add(textField);
        textField.setOnAction(this::createNewDictionary);
    }

    private void createNewDictionary(ActionEvent event) {
        TextField textField = (TextField) event.getSource();
        String dictionaryName = textField.getText();
        UncheckedRunnable<IOException> crateNewFile = () -> {
            if (!dir.exists())
                dir.mkdir();
            Dictionary dictionary = new Dictionary(language);
            PersistenceManager persistenceManager = new BasicPersistenceManager();
            persistenceManager.persist(dictionary, path + "/" + dictionaryName);
            Label createdDictionary = new Label(dictionaryName);
            int entryIndex = dictionaries.getItems().indexOf(textField);
            dictionaries.getItems().set(entryIndex, createdDictionary);
        };
        try {
            confirmExpression(dictionaryName.matches("[0-9A-Za-z\\-_]+"), crateNewFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private Label createInfoLabel() {
        return new Label("Choose dictionary:");
    }

    @NotNull
    private ListView<Node> createDictionariesList() {
        ListView<Node> dictionariesList = new ListView<>();
        File[] files;
        if (dir.exists() && (files = dir.listFiles()).length > 0)
            setFilesToDictionaryList(dictionariesList, files);
        return dictionariesList;
    }

    private void setFilesToDictionaryList(ListView<Node> dictionariesList, File[] files) {
        for (File file : files)
            dictionariesList.getItems().add(getTextForFile(file.getName()));
    }

    @NotNull
    private Text getTextForFile(String fileName) {
        int dotIndex = fileName.indexOf('.');
        String pureFileName = fileName.substring(0, dotIndex);
        Text textField = new Text(pureFileName);
        return textField;
    }

    private void setUp() {
        BorderPane.setAlignment(info, Pos.BOTTOM_CENTER);
        setTop(info);

        VBox buttonBox = new VBox(MIN_SPACE);
        buttonBox.getChildren().addAll(newButton, loadButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        HBox contentAndControlBox = new HBox(MIN_SPACE);
        contentAndControlBox.getChildren().addAll(buttonBox, dictionaries);
        contentAndControlBox.setAlignment(Pos.TOP_CENTER);
        BorderPane.setAlignment(contentAndControlBox, Pos.TOP_CENTER);
        setCenter(contentAndControlBox);

        BorderPane.setAlignment(backButton, Pos.BOTTOM_LEFT);
        setLeft(backButton);

        setPadding(INSETS);
    }

    private void equalizeButtons() {
        Button[] buttons = {loadButton, newButton, deleteButton};
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
