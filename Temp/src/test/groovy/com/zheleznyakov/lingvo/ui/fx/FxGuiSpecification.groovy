package com.zheleznyakov.lingvo.ui.fx

import com.zheleznyakov.lingvo.basic.Language
import com.zheleznyakov.lingvo.dictionary.persistence.PersistenceTestHelper
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import javafx.scene.Node
import javafx.scene.control.ComboBox
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import javafx.stage.Stage
import org.junit.After
import org.junit.Before
import org.testfx.api.FxRobotInterface
import org.testfx.api.FxToolkit
import org.testfx.framework.junit.ApplicationTest
import spock.lang.Specification

import java.util.concurrent.TimeoutException

class FxGuiSpecification extends Specification {
    protected static final String ROOT_DIR = ZhConfigFactory.get().getString("languageDirRoot")
    protected static final String FORWARD_BUTTON_ID = "#forwardButton";
    protected static final String EXIT_BUTTON_ID = "#exitButton";

    GuiTestMixin fx

    @Before
    def generalSetup() {
        fx = new GuiTestMixin()
        fx.internalBefore()
        fx.setUp();
    }

    @After
    def generalCleanup() {
        fx.tearDown()
        PersistenceTestHelper.removeFolder(ROOT_DIR)
    }

    protected <T extends Node> T find(String query) {
        return ++fx.lookup(query).queryAll().iterator() as T
    }

    protected <T> void chooseValueInComboBox(ComboBox<T> comboBox, String value) {
        fx.clickOn(comboBox);
        fx.moveTo(value);
        fx.clickOn(value);
    }

    protected FxRobotInterface clickOn(String query) {
        return fx.clickOn(query)
    }

    protected loadDictionaryPane(Language language) {
        ComboBox<Language> comboBox = find("#dropBox")
        chooseValueInComboBox(comboBox, language.name())
        clickOn(FORWARD_BUTTON_ID)
    }

    static class GuiTestMixin extends ApplicationTest {
        void setUp() {
            launch(UiFxMain.class)
        }

        @Override
        void start(Stage stage) {
            stage.show()
        }

        void tearDown() throws TimeoutException {
            FxToolkit.hideStage();
            release([].toArray() as KeyCode[]);
            release([].toArray() as MouseButton[]);
        }
    }
}
