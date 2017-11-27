package com.zheleznyakov.lingvo.dictionary.persistence

import com.zheleznyakov.lingvo.basic.Word
import com.zheleznyakov.lingvo.dictionary.Dictionary
import com.zheleznyakov.lingvo.language.en.word.EnAdjective
import com.zheleznyakov.lingvo.language.en.word.EnNoun
import com.zheleznyakov.lingvo.learning.LearningDictionary
import com.zheleznyakov.lingvo.learning.WordTester
import spock.lang.Specification

import static com.zheleznyakov.lingvo.dictionary.persistence.PersistenceManager.DIC_EXTENSION
import static com.zheleznyakov.lingvo.dictionary.persistence.PersistenceManager.LD_EXTENSION
import static com.zheleznyakov.lingvo.language.Language.ENGLISH

class BasicPersistenceManagerSpec extends Specification {
    static String ROOT_PATH = "src/test/resources/dictionaries/";

    def cleanup() {
        PersistenceTestHelper.removeFolder(ROOT_PATH)
    }

    def "Persist and load a dictionary"() {
        given: "a non-empty dictionary"
        Dictionary dictionary = new Dictionary(ENGLISH)
        dictionary.add(EnNoun.build("word"), "слово")
        String title = "temp"
        dictionary.title = title

        and: "a basic persistence manager"
        PersistenceManager persistenceManager = new BasicPersistenceManager()

        expect: "that the file for persistence does not exists"
        String fileName = title + DIC_EXTENSION
        String path = ROOT_PATH + ENGLISH.toLowerCase() + "/"
        !new File(path + fileName).exists()

        when: "the dictionary is persisted"
        persistenceManager.persist(dictionary)

        and: "is loaded back"
        Dictionary loadedDictionary = persistenceManager.load(Dictionary.class, ENGLISH, fileName)

        then: "the file for persistence exists"
        new File(path + fileName).exists()

        and: "the loaded dictionary is the same as the original one"
        dictionary.language == loadedDictionary.language
        dictionary.asMap() == loadedDictionary.asMap()
        dictionary.title == loadedDictionary.title
    }

    def "Persist and load a learning dictionary"() {
        given: "a non-empty learning dictionary"
        LearningDictionary dictionary = getLearningDictionaryWithNonNullStatistics()
        String title = "test"
        dictionary.title = title

        and: "a basic persistence manager"
        PersistenceManager persistenceManager = new BasicPersistenceManager()

        expect: "that the file for persistence does not exists"
        String fileName = title + LD_EXTENSION
        String path = ROOT_PATH + dictionary.getLanguage().toLowerCase() + "/"
        !new File(path + fileName).exists()

        when: "the dictionary is persisted"
        persistenceManager.persist(dictionary)

        and: "is loaded back"
        Dictionary loadedDictionary = persistenceManager.load(LearningDictionary.class, dictionary.getLanguage(), fileName)

        then: "the file for persistence exists"
        new File(path + fileName).exists()

        and: "the loaded dictionary is the same as the original one"
        dictionary.class == loadedDictionary.class
        dictionary.language == loadedDictionary.language
        dictionary.asMap() == loadedDictionary.asMap()
        dictionary.title == loadedDictionary.title
    }

    private static LearningDictionary getLearningDictionaryWithNonNullStatistics() {
        LearningDictionary dictionary = new LearningDictionary(ENGLISH)
        Word word = EnNoun.build("word")
        String meaning = "слово"
        dictionary.add(word, meaning)
        new WordTester(dictionary).with {
            start()
            nextWord
            test meaning
        }
        assert dictionary.getCount(word) == 1
        dictionary.add(EnAdjective.build("green"), "зелёный")
        return dictionary
    }


}