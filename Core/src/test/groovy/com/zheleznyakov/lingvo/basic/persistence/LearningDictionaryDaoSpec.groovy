package com.zheleznyakov.lingvo.basic.persistence

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig
import com.zheleznyakov.lingvo.basic.implementations.FakeEnglish
import com.zheleznyakov.lingvo.helpers.TestHelper
import spock.lang.Specification

class LearningDictionaryDaoSpec extends Specification {

    def "Convert LearningDictionary into a PersistenceObject"() {
        given: "a learning dictionary DAO"
        PersistenceManager persistenceManager = Mock()
        LearningDictionaryDao dao = [persistenceManager]

        and: "an empty learning dictionary"
        LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE]

        expect: "the dictionary config to be default"
        TestHelper.areConfigsEqual(dictionary.config, LearningDictionaryConfig.default)

        when: "the DAO persists the dictionary"
        dao.persist(dictionary)

        then: "the PersistenceEntity describing the dictionary is passed to the persistence manager"
        1 * persistenceManager.persist({
            it != null
            it instanceof PersistenceEntity
            it.entityClass == LearningDictionary
        } as PersistenceEntity)
    }

}