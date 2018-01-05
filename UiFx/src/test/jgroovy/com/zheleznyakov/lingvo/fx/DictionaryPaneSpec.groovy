package com.zheleznyakov.lingvo.fx

import com.zheleznyakov.lingvo.dictionary.persistence.PersistenceManager
import com.zheleznyakov.lingvo.en.EnWord
import com.zheleznyakov.lingvo.en.word.EnNoun
import com.zheleznyakov.lingvo.fx.exceptions.UiFxException

import static com.zheleznyakov.lingvo.basic.Language.ENGLISH

class DictionaryPaneSpec extends FxGuiSpecification {

    def "If dictionary file does not exist, then clicking on the Forward button loads the dictionary page and create the file"() {
        expect: "the dictionary file does not exists"
        File file = new File(ROOT_DIR + "/english/english.dic")
        !file.exists()

        when: "English language is chosen and the forward button is clicked"
        loadDictionaryPane(ENGLISH)

        then: "the English dictionary page is loaded"
        find("#dictionaryPane-english")
        file.exists()
    }

    def "If the dictionary file exists, load it when clicking on the Forward button"() {
        given: "a persisted English dictionary"
        EnWord word = EnNoun.build("word")
        PersistenceTestHelper.ensureDictionaryExistence(ENGLISH, word)

        when: "English Dictionary Page is loaded"
        loadDictionaryPane(ENGLISH)

        then: "the persisted dictionary is loaded"
        Dictionary loadedDictionary = find("#dictionaryPane-english").getDictionary()
        loadedDictionary.words.size() == 1
        loadedDictionary.getMeaning(word)
    }

    def "When DictionaryPane fails to load the dictionary, throw"() {
        given: "a Persistence Manager that will throw"
        PersistenceManager persistenceManager = Mock()
        persistenceManager.persist(_) >> {throw new IOException("Mock Exception")}
        PersistenceTestHelper.mockPersistenceManager(persistenceManager)

        expect: "when loading English dictionary pane, an UiFxException is thrown"
        try {
            loadDictionaryPane(ENGLISH)
        } catch (UiFxException ex) {
            ex.message.startsWith "Failed to create DictionaryPane"
        }
    }
}