package com.zheleznyakov.lingvo.ui.fx;

import static org.junit.Assert.assertEquals;

import java.security.Permission;

import org.junit.Test;

import com.zheleznyakov.lingvo.language.Language;

public class UiFxMainTest extends BaseTest {

    @Test
    public void main_page_is_a_choose_pain() {
        assertChoosePaneLayout("Choose language:", Language.class);
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

//    @Test
//    public void test_clicking

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