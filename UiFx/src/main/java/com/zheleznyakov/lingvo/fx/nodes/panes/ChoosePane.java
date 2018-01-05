package com.zheleznyakov.lingvo.fx.nodes.panes;

import static com.zheleznyakov.lingvo.util.Util.validateArgument;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.zheleznyakov.lingvo.fx.Config;
import com.zheleznyakov.lingvo.fx.nodes.buttons.ExitButton;
import com.zheleznyakov.lingvo.fx.nodes.buttons.ForwardButton;

public class ChoosePane<T> extends BorderPane {
    private final Label info;
    private final ComboBox<T> objectsBox;
    private final Button confirmButton;
    private final Button exitButton;

    public ChoosePane(String text, T[] objects, T defaultObject) {
        info = new Label(text);
        objectsBox = createDropBox(Arrays.asList(objects), defaultObject);
        confirmButton = new ForwardButton();
        exitButton = new ExitButton();
        setUpLayout();
        setId("choosePane-" + defaultObject.getClass().getSimpleName().toLowerCase());
    }

    @NotNull
    private ComboBox<T> createDropBox(List<T> objects, T defaultObject) {
        Util.validateArgument(objects.contains(defaultObject) || defaultObject == null,
                "List [{}] does not contain [{}]", objects, defaultObject);
        ComboBox<T> objectsBox = new ComboBox<>(FXCollections.observableArrayList(objects));

        objectsBox.setValue(defaultObject == null ? objects.get(0) : defaultObject);
        objectsBox.setId("dropBox");
        return objectsBox;
    }

    private void setUpLayout() {
        HBox controlBox = new HBox(Config.MIN_SPACE);
        controlBox.getChildren().addAll(objectsBox, confirmButton);
        controlBox.setAlignment(Pos.CENTER);

        VBox mainBox = new VBox(Config.MIN_SPACE);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(info, controlBox);

        BorderPane.setAlignment(exitButton, Pos.BOTTOM_LEFT);

        setPadding(Config.INSETS);
        setCenter(mainBox);
        setLeft(exitButton);
    }

    public T getChosen() {
        return objectsBox.getValue();
    }

    public void setOnForward(EventHandler<ActionEvent> handler) {
        confirmButton.setOnAction(handler);
    }

    public void setOnExit(EventHandler<ActionEvent> handler) {
        exitButton.setOnAction(handler);
    }

}
