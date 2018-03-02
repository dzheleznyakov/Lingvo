package com.zheleznyakov.lingvo.basic.persistence

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig
import com.zheleznyakov.lingvo.basic.implementations.FakeEnglish
import com.zheleznyakov.lingvo.basic.implementations.FakeEnglishConverter
import com.zheleznyakov.lingvo.basic.persistence.converters.ConvertingWorkshop
import com.zheleznyakov.lingvo.basic.persistence.entities.ObjectPersistenceEntity
import com.zheleznyakov.lingvo.helpers.TestHelper
import spock.lang.Specification

class LearningDictionaryDaoSpec extends Specification {

    def setupSpec() {
        ConvertingWorkshop.registerHandler(FakeEnglish.class, new FakeEnglishConverter())
    }

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

        then: "the ObjectPersistenceEntity describing the dictionary is passed to the persistence manager"
        1 * persistenceManager.persist({
            it.entityClass == LearningDictionary
            it.fields["language"].entityClass == FakeEnglish
            it.fields["config"].entityClass == LearningDictionaryConfig
            it.fields["config"].fields["mode"].value == LearningDictionaryConfig.Mode.FORWARD.name()
            it.fields["config"].fields["maxLearnCount"].value == 30
            it.fields["config"].fields["strict"].value == false
            it.fields["records"].size() == 0
        } as ObjectPersistenceEntity)
    }

}