package com.zheleznyakov.lingvo.ui.fx.panes;

import static com.zheleznyakov.lingvo.ui.fx.Config.INSETS;
import static com.zheleznyakov.lingvo.ui.fx.Config.MIN_SPACE;
import static com.zheleznyakov.lingvo.util.Util.validateArgument;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.zheleznyakov.lingvo.ui.fx.buttons.ForwardButton;

public class ChoosePane<T> extends VBox {
    private final Label info;
    private final ComboBox<T> objectsBox;
    private final Button confirmButton;

    public ChoosePane(String text, T[] objects, T defaultObject) {
        super(MIN_SPACE);
        info = new Label(text);
        objectsBox = createLanguageBox(Arrays.asList(objects), defaultObject);
        confirmButton = new ForwardButton();
        setUp();
    }

    @NotNull
    private ComboBox<T> createLanguageBox(List<T> objects, T defaultObject) {
        validateArgument(objects.contains(defaultObject),
                "List [{}] does not contain [{}]", objects, defaultObject);
        ComboBox<T> objectsBox = new ComboBox<>();
        for (T object : objects)
            objectsBox.getItems().add(object);
        objectsBox.setValue(defaultObject);
        return objectsBox;
    }

    private void setUp() {
        HBox controlBox = new HBox(MIN_SPACE);
        controlBox.getChildren().addAll(objectsBox, confirmButton);
        controlBox.setAlignment(Pos.CENTER);
        getChildren().addAll(info, controlBox);
        setPadding(INSETS);
        setAlignment(Pos.CENTER);
    }

    public T getChosen() {
        return objectsBox.getValue();
    }

    public void setOnChosen(javafx.event.EventHandler<ActionEvent> event) {
        confirmButton.setOnAction(event);
    }

}
