package zh.lingvo.persistence.mongodb

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import spock.lang.IgnoreIf
import spock.lang.Requires
import spock.lang.Specification
import zh.lingvo.domain.User
import zh.lingvo.utils.Id

class MongoPersistenceManagerSpec extends Specification {
    private static final String TEST_DB_NAME = 'zhlingvotest'
    private static MongoClient globalClient

    private MongoPersistenceManager persistenceManager = new MongoPersistenceManager(TEST_DB_NAME)

    def setupSpec() {
        globalClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"))
    }

    def cleanup() {
        globalClient.dropDatabase(TEST_DB_NAME)
        persistenceManager.close()
    }

    def cleanupSpec() {
        if (globalClient != null)
            globalClient.close()
    }

    @Requires({
        def client
        try {
            client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"))
            client.getAddress()
            return true
        } catch (Exception e) {
            e.printStackTrace()
            return false
        } finally {
            if (client != null)
                client.close()
        }
    })
    def "MongoPersistenceManager should be able to persist a user"() {
        given: 'a user'
        def user = new User(
                id: new Id<>('mockId'),
                name: 'Jon Snow')

        when: 'a user is persisted'
        def success = persistenceManager.persistUser(user)

        then: 'the record appears in the db'
        success
    }
}
