package com.zheleznyakov.lingvo.ui.fx;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import com.zheleznyakov.lingvo.ui.fx.panes.ChoosePane;

public class BaseTest extends ApplicationTest {
    protected Stage stage;

    @Before
    public void setUp() throws Exception {
        ApplicationTest.launch(UiFxMain.class);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.show();
    }

    @After
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @SuppressWarnings("unchecked")
    protected  <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }

    protected void sleepUninterruptibly(long millis) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < millis)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("The thread was interrupted");
            }
    }

    protected <T> void assertChoosePaneLayout(String labelText, Class<T> dropBoxElementsClass) {
        Parent root = stage.getScene().getRoot();
        ComboBox dropBox = find("#dropBox");

        assertTrue(root instanceof ChoosePane);
        verifyThat(".label", hasText(labelText));
        assertFalse(dropBox.getItems().isEmpty());
        assertTrue(dropBoxElementsClass.isInstance(dropBox.getItems().get(0)));
        assertNotNull(find("#forwardButton"));
        assertNotNull(find("#exitButton"));
    }
}
