package com.zheleznyakov.lingvo.basic.persistence

import com.zheleznyakov.lingvo.basic.implementations.FakeDao
import spock.lang.Specification

class DaoSpec extends Specification {
    private PersistenceManager persistenceManager = Mock()

    def "Test persisting an Object with a DAO"() {
        given: "an object to persist"
        TestClass1 object = []

        and: "a DAO for the corresponding class"
        Dao<TestClass1> dao = [persistenceManager] as FakeDao

        when: "the object is persisted"
        dao.persist(object)

        then: "persistence manager is called to persist"
        1 * persistenceManager.persist(_ as PersistenceEntity)
    }

    private static class TestClass1 {
    }

}