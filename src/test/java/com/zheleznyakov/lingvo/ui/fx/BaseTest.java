package com.zheleznyakov.lingvo.ui.fx;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

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

    protected void sleepUninterruptibly(long time) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < time)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("The thread was interrupted");
            }
    }
}
