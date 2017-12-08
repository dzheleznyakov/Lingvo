package com.zheleznyakov.lingvo.ui.fx

import Language
import javafx.scene.control.ComboBox
import org.testfx.matcher.control.ComboBoxMatchers
import spock.lang.Unroll

import java.security.Permission

import static Language.ENGLISH
import static Language.SPANISH
import static org.junit.Assert.assertThat
import static org.testfx.api.FxAssert.verifyThat
import static org.testfx.util.NodeQueryUtils.hasText

class MainPageSpec extends FxGuiSpecification {

    def "Verify the elements of the main page"() {
        expect: "main page to have all necessary elements"
        ComboBox<Language> dropBox = find("#dropBox")
        verifyThat(".label", hasText("Choose language:"))
        !dropBox.getItems().isEmpty()
        assertThat(dropBox, ComboBoxMatchers.hasSelectedItem(ENGLISH))
        find(FORWARD_BUTTON_ID)
        find(EXIT_BUTTON_ID)
    }

    def "Test that the app exits when the Exit button is clicked"() {
        setup: "set a testable system security manager"
        SecurityManager securityManager = Mock()
        System.securityManager = securityManager

        when: "the Exit button is clicked"
        clickOn(EXIT_BUTTON_ID)

        then: "the system's exit attempt is caught"
        true
        1 * securityManager.checkExit(1) >> {throw new SecurityException()}

        cleanup: "unset security manager"
        System.securityManager = null
    }

    def "Assert the language combo box"() {
        given: "a language combo box from the ui main page"
        ComboBox<Language> comboBox = find("#dropBox")

        expect: "that it contains all the languages"
        comboBox
        assertThat(comboBox, ComboBoxMatchers.containsItems(Language.values()))

        and: "the default language is English"
        assertThat(comboBox, ComboBoxMatchers.hasSelectedItem(ENGLISH))

        when: "Spanish language is clicked"
        chooseValueInComboBox(comboBox, SPANISH.name())

        then: "it remains selected"
        assertThat(comboBox, ComboBoxMatchers.hasSelectedItem(SPANISH))
    }

    @Unroll
    def "When #language is chosen and the Forward button is clicked, then #dictionaryPaneId is loaded"() {
        when: "English language is chosen and the forward button is clicked"
        loadDictionaryPane(language)

        then: "the English dictionary page is loaded"
        find(dictionaryPaneId)

        where: "the parameters are"
        language || dictionaryPaneId
        ENGLISH  || "#dictionaryPane-english"
        SPANISH  || "#dictionaryPane-spanish"
    }

    def "When starting the second instance, an exception is thrown"() {
        when: "starting the second instance"
        UiFxMain.main(null);

        then: "an IllegalStateException is thrown"
        thrown(IllegalStateException)
    }

    private static class ExitException extends SecurityException {
        public final int status

        ExitException(int status) {
            this.status = status
        }
    }

    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        void checkPermission(Permission perm) {
        }

        @Override
        void checkPermission(Permission perm, Object context) {
        }

        @Override
        void checkExit(int status) {
            super.checkExit(status)
            throw new ExitException(status)
        }
    }

}