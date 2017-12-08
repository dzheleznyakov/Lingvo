package com.zheleznyakov.lingvo.dictionary.persistence

import spock.lang.Specification

class PersistenceUtilSpec extends Specification {

    def "When getting a PersistenceManager via PersistenceUtil, it returns the same object"() {
        when: "getting a PersistenceManager twice"
        PersistenceManager persistenceManager1 = PersistenceUtil.get()
        PersistenceManager persistenceManager2 = PersistenceUtil.get()

        then: "both times the same object was returned"
        persistenceManager1 == persistenceManager2
    }

}
