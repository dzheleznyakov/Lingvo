package com.zheleznyakov.lingvo.ui.fx.nodes.panes;

import static com.zheleznyakov.lingvo.dictionary.persistence.PersistenceManager.DIC_EXTENSION;
import static com.zheleznyakov.lingvo.ui.fx.Config.INSETS;
import static com.zheleznyakov.lingvo.ui.fx.Config.MIN_SPACE;

import java.io.File;
import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.dictionary.Dictionary;
import com.zheleznyakov.lingvo.dictionary.persistence.PersistenceUtil;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.ui.fx.nodes.buttons.BackButton;
import com.zheleznyakov.lingvo.ui.fx.nodes.buttons.DownButton;
import com.zheleznyakov.lingvo.ui.fx.nodes.buttons.MinusButton;
import com.zheleznyakov.lingvo.ui.fx.nodes.buttons.PlusButton;
import com.zheleznyakov.lingvo.ui.fx.nodes.buttons.UpButton;
import com.zheleznyakov.lingvo.util.ZhConfigFactory;

public class DictionaryPane extends BorderPane {

    private final String ROOT_PATH = ZhConfigFactory.get().getString("languageDirRoot");
    private final String path;
    private final Language language;
    private Dictionary dictionary;

    private final Label info;
    private final TableView<WordEntry> words;
    private final Button backButton;

    private final Button up;
    private final Button down;
    private final Button add;
    private final Button delete;

    public DictionaryPane(Language language) throws IOException, ClassNotFoundException {
        path = ROOT_PATH + "/" + language.name().toLowerCase() + "/";
        this.language = language;
        ensureDictionaryForThisLanguage();

        info = createInfoLabel();
        words = createWordView();
        backButton = new BackButton();

        up = new UpButton();
        add = new PlusButton();
        delete = new MinusButton();
        down = new DownButton();

        setUpLayout();
        setId("dictionaryPane-" + language.name().toLowerCase());
    }

    private void ensureDictionaryForThisLanguage() throws IOException, ClassNotFoundException {
        String fileName = language.toLowerCase() + DIC_EXTENSION;
        File dictionaryFile = new File(path + fileName);
        if (!dictionaryFile.exists())
            createAndPersistDictionary(path);
        else
            dictionary = PersistenceUtil.get().load(Dictionary.class, language, fileName);
//        addTestWord();
    }

//    private void addTestWord() {
//        EnWord word = EnNoun.build("word");
//        dictionary.add(word, "слово");
//    }

    private void createAndPersistDictionary(String pathToDictionary) throws IOException {
        dictionary = new Dictionary(language);
        dictionary.setTitle(language.toLowerCase());
        PersistenceUtil.get().persist(dictionary);
    }

    @NotNull
    private Label createInfoLabel() {
        return new Label("Choose word:");
    }

    @NotNull
    private TableView<WordEntry> createWordView() {
        ObservableList<WordEntry> wordEntries = FXCollections.observableArrayList();
        dictionary.getWords()
                .stream()
                .map(WordEntry::new)
                .forEach(wordEntries::add);
        TableView<WordEntry> wordView = new TableView<>(wordEntries);
        TableColumn<WordEntry, String> mainFormColumn = getWordViewColumn("Word", "mainForm");
        TableColumn<WordEntry, String> partOfSpeechColumn = getWordViewColumn("p.o.s", "partOfSpeech");
        TableColumn<WordEntry, String> meaningColumn = getWordViewColumn("Meaning", "meaning");
        wordView.getColumns().addAll(mainFormColumn, partOfSpeechColumn, meaningColumn);
        wordView.setId("wordView");
        return wordView;
    }

    @NotNull
    private TableColumn<WordEntry, String> getWordViewColumn(String columnTitle, String propertyName) {
        TableColumn<WordEntry, String> mainFormColumn = new TableColumn<>(columnTitle);
        mainFormColumn.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        return mainFormColumn;
    }

    private void setUpLayout() {
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

    public Dictionary getDictionary() {
        return dictionary;
    }

    //    public void setOnBack(EventHandler<ActionEvent> handler) {
//        backButton.setOnAction(handler);
//    }

    public class WordEntry {
        private final StringProperty mainForm;
        private final StringProperty partOfSpeech;
        private final StringProperty meaning;

        public WordEntry(Word word) {
            mainForm = new SimpleStringProperty(word.getMainForm());
            partOfSpeech = new SimpleStringProperty(word.getPartOfSpeech().brief);
            meaning = new SimpleStringProperty(dictionary.getMeaning(word));
        }

        public String getMainForm() {
            return mainForm.get();
        }

        public String getPartOfSpeech() {
            return partOfSpeech.get();
        }

        public String getMeaning() {
            return meaning.get();
        }
    }

}
