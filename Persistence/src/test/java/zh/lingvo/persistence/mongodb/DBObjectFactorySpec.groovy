package zh.lingvo.persistence.mongodb

import spock.lang.Specification
import zh.lingvo.domain.User
import zh.lingvo.utils.Id

class DBObjectFactorySpec extends Specification {
    private DBObjectFactory factory = []

    def "DBObjectFactory can convert User"() {
        given: 'a user entity'
        def user = new User(
                id: new Id<>('mockId'),
                name: 'Jon Snow')

        when: 'the user is converted into a DBObject'
        def dBObject = factory.convert(user)

        then: 'the simple fields are handled by the converter'
        dBObject.toMap() == [_id: 'mockId', name: 'Jon Snow']
    }
}
