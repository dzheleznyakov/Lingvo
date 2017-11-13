package com.zheleznyakov.lingvo.ui.fx;

import static com.zheleznyakov.lingvo.language.Language.ENGLISH;
import static com.zheleznyakov.lingvo.language.Language.SPANISH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.security.Permission;

import org.junit.Test;
import org.testfx.matcher.control.ComboBoxMatchers;

import javafx.scene.control.ComboBox;

import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.ui.fx.nodes.buttons.ForwardButton;

public class MainPageTest extends AbstractUiFxTest {

    @Test
    public void assert_layout_of_main_page() {
        ComboBox<Language> dropBox = find("#dropBox");
        
        verifyThat(".label", hasText("Choose language:"));
        assertFalse(dropBox.getItems().isEmpty());
        assertThat(dropBox, ComboBoxMatchers.hasSelectedItem(ENGLISH));
        assertNotNull(find(FORWARD_BUTTON_ID));
        assertNotNull(find(EXIT_BUTTON_ID));
    }

    @Test
    public void test_exit_when_clicking_on_exit_button() throws InterruptedException {
        System.setSecurityManager(new NoExitSecurityManager());
        try {
            rightClickOn(EXIT_BUTTON_ID);
            fail();
        } catch (ExitException e) {
            assertEquals(1, e.status);
        } finally {
            System.setSecurityManager(null);
        }
    }

    @Test
    public void check_combo_box() {
        ComboBox<Language> comboBox = find("#dropBox");

        assertNotNull(comboBox);
        assertThat(comboBox, ComboBoxMatchers.containsItems(Language.values()));
        assertThat(comboBox, ComboBoxMatchers.hasSelectedItem(ENGLISH));

        chooseValueInComboBox(comboBox, SPANISH.name());

        assertThat(comboBox, ComboBoxMatchers.hasSelectedItem(SPANISH));
    }

    @Test
    public void test_clicking_forward_button() throws InterruptedException {
        ComboBox<Language> comboBox = find("#dropBox");
        ForwardButton forwardButton = find(FORWARD_BUTTON_ID);

        chooseValueInComboBox(comboBox, ENGLISH.name());
        clickOn(forwardButton);

        assertNotNull(find("#dictionaryPane-english"));
    }

    @Test(expected = IllegalStateException.class)
    public void start_main_method() {
        UiFxMain.main(null);
    }

    private static class ExitException extends SecurityException {
        public final int status;

        public ExitException(int status) {
            this.status = status;
        }
    }

    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
        }

        @Override
        public void checkPermission(Permission perm, Object context) {
        }

        @Override
        public void checkExit(int status) {
            throw new ExitException(status);
        }
    }

}