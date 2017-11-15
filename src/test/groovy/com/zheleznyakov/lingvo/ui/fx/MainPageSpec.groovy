package com.zheleznyakov.lingvo.ui.fx

import com.zheleznyakov.lingvo.dictionary.persistence.PersistenceHelper
import com.zheleznyakov.lingvo.dictionary.persistence.PersistenceManager
import com.zheleznyakov.lingvo.language.Language
import com.zheleznyakov.lingvo.ui.fx.exceptions.UiFxException
import com.zheleznyakov.lingvo.util.ZhConfigFactory
import javafx.scene.control.ComboBox
import org.testfx.matcher.control.ComboBoxMatchers

import java.security.Permission

import static com.zheleznyakov.lingvo.language.Language.ENGLISH
import static com.zheleznyakov.lingvo.language.Language.SPANISH
import static org.junit.Assert.assertThat
import static org.testfx.api.FxAssert.verifyThat
import static org.testfx.util.NodeQueryUtils.hasText

class MainPageSpec extends FxGuiSpecification {
    private static final String ROOT_DIR = ZhConfigFactory.get().getString("languageDirRoot")

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

    def "If dictionary file does not exist, then clicking on the Forward button loads the dictionary page and create the file"() {
        expect: "the dictionary file does not exists"
        File file = new File(ROOT_DIR + "/english/english.dic")
        !file.exists()

        when: "English language is chosen and the forward button is clicked"
        loadDictionaryPane(ENGLISH)

        then: "the English dictionary page is loaded"
        find("#dictionaryPane-english")
        file.exists()

        cleanup: "remove the dictionary folder"
        PersistenceHelper.removeFolder(ROOT_DIR)
    }

    def "If the dictionary file exists, load it when clicking on the Forward button"() {

    }

    def "When starting the second instance, an exception is thrown"() {
        when: "starting the second instance"
        UiFxMain.main(null);

        then: "an IllegalStateException is thrown"
        thrown(IllegalStateException)
    }

    def "When DictionaryPane fails to load the dictionary, throw"() {
        given: "a Persistence Manager that will throw"
        PersistenceManager persistenceManager = Mock()
        persistenceManager.persist(_) >> {throw new IOException("Mock Exception")}
        PersistenceHelper.mockPersistenceManager(persistenceManager)

        expect: "when loading English dictionary pane, an UiFxException is thrown"
        try {
            loadDictionaryPane(ENGLISH)
        } catch (UiFxException ex) {
            ex.message.startsWith "Failed to create DictionaryPane"
        }

        cleanup: "remove the dictionary folder"
        PersistenceHelper.removeFolder(ROOT_DIR)
    }

    private loadDictionaryPane(Language language) {
        ComboBox<Language> comboBox = find("#dropBox")
        chooseValueInComboBox(comboBox, language.name())
        clickOn(FORWARD_BUTTON_ID)
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