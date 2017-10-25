package com.zheleznyakov.lingvo.ui.fx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.security.Permission;

import org.junit.Test;
import org.testfx.service.finder.NodeFinder;
import org.testfx.service.finder.impl.NodeFinderImpl;
import org.testfx.service.finder.impl.WindowFinderImpl;

import javafx.scene.control.ComboBox;

public class UiFxMainTest extends BaseTest {

    @Test
    public void should_contain_label() {
        NodeFinder nodeFinder = new NodeFinderImpl(new WindowFinderImpl());
        verifyThat(".label", hasText("Choose language:"));
    }

    @Test
    public void test_exit_when_clicking_on_exit_button() throws InterruptedException {
        System.setSecurityManager(new NoExitSecurityManager());
        try {
            rightClickOn("#exitButton");
        } catch (ExitException e) {
            assertEquals(1, e.status);
        } finally {
            System.setSecurityManager(null);
        }
    }

    @Test
    public void language_drop_box_is_not_empty() {
        ComboBox dropBox = find("#dropBox");
        assertFalse(dropBox.getItems().isEmpty());
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
            super.checkExit(status);
            throw new ExitException(status);
        }
    }
}