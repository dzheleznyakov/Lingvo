package com.zheleznyakov.lingvo.basic.persistence

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionaryConfig
import com.zheleznyakov.lingvo.basic.dictionary.Record
import com.zheleznyakov.lingvo.basic.implementations.FakeEnglish
import com.zheleznyakov.lingvo.basic.implementations.FakeEnglishConverter
import com.zheleznyakov.lingvo.basic.persistence.converters.ConvertingWorkshop
import com.zheleznyakov.lingvo.basic.persistence.entities.ObjectPersistenceEntity
import com.zheleznyakov.lingvo.basic.persistence.entities.PersistenceEntity
import com.zheleznyakov.lingvo.helpers.TestHelper
import spock.lang.Specification

class LearningDictionaryDaoSpec extends Specification {
    private Map<String, Record> recordsByMainForm

    def setupSpec() {
        ConvertingWorkshop.registerHandler(FakeEnglish.class, new FakeEnglishConverter())
    }

    def "Convert an empty LearningDictionary into a PersistenceEntity"() {
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
            it.fields["config"].fields["mode"].value == LearningDictionaryConfig.default.mode.name()
            it.fields["config"].fields["maxLearnCount"].value == LearningDictionaryConfig.default.maxLearnCount
            it.fields["config"].fields["strict"].value == LearningDictionaryConfig.default.strict
            it.fields["records"].size() == 0
        } as ObjectPersistenceEntity)
    }

    def "Convert a LearningDictionary with records into a PersistenceEntity"() {
        given: "a learning dictionary DAO"
        PersistenceManager persistenceManager = Mock()
        LearningDictionaryDao dao = [persistenceManager]

        and: "a learning dictionary with 10 words"
        LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE]
        TestHelper.addRecordsToDictionary(dictionary, 10)

        when: "the DAO persists the dictionary"
        dao.persist(dictionary)

        then: "the ObjectPersistenceEntity describing the dictionary is passed to the persistence manager"
        1 * persistenceManager.persist({
            it.fields["records"].size() == 10
        } as ObjectPersistenceEntity)
    }

    def "Check converted record in a converted LearningDictionary"() {
        given: "a learning dictionary DAO"
        PersistenceManager persistenceManager = Mock()
        LearningDictionaryDao dao = [persistenceManager]

        and: "a learning dictionary with 1 word"
        LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE]
        TestHelper.addRecordsToDictionary(dictionary, 1)

        when: "the DAO persists the dictionary"
        dao.persist(dictionary)
        Record record = dictionary.records.iterator().next()

        then: "the ObjectPersistenceEntity describing the dictionary is passed to the persistence manager"
        1 * persistenceManager.persist({
            it.fields["records"].size() == 1
            it.fields["records"].values.get(0).entityClass == Record
        } as ObjectPersistenceEntity)
    }

}