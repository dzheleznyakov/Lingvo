package com.zheleznyakov.lingvo.util

import spock.lang.Specification
import spock.lang.Unroll

class UtilSpeck extends Specification {

    def "When expression is true, then validateArgument passes"() {
        expect: "validation to pass"
        Util.validateArgument(true, "Doesn't matter")
    }

    def "When expression is false, then validateArgument throws"() {
        when: "validating a false argument"
        String message = "Message"
        Util.validateArgument(false, message)

        then: "an IllegalArgumentException is thrown"
        IllegalArgumentException e = thrown()
        e.message == message
    }

    def "When expression is true, then validateState passes"() {
        expect: "validation to pass"
        Util.validateState(true, "Doesn't matter")
    }

    def "When expression is false, then validatateState throws"() {
        when: "validating a false argument"
        String message = "Message"
        Util.validateState(false, message)

        then: "an IllegalStateException is thrown"
        def e = thrown(IllegalStateException)
        e.message == message
    }

    @Unroll
    def "Test message formatting with pattern \"#pattern\""() {
        when: "formatting message pattern"
        String message = Util.format(pattern, arguments)

        then: "the message is correctly formatted"
        message == expectedMessage

        where: "the parameters are"
        pattern                     | arguments                || expectedMessage
        null                        | ["a", false].toArray()   || null
        ""                          | ["argument"].toArray()   || ""
        "No arguments"              | [].toArray()             || "No arguments"
        "Missing argument {}"       | [].toArray()             || "Missing argument "
        "true={}"                   | [true].toArray()         || "true=true"
        "{}=true"                   | [true].toArray()         || "true=true"
        "one {}, two {}"            | [1, 2].toArray()         || "one 1, two 2"
        "Too many arguments: {}"    | ["one", "two"].toArray() || "Too many arguments: one"
        "Too few arguments: {}, {}" | [1d].toArray()           || "Too few arguments: 1.0, "
    }

    def "Test getRandom() returns an element from a collection"() {
        given: "a collection"
        Collection<Integer> objects = [1, 2, 3, 4, 5, 6].toSet()

        expect: "the getRandom() method returns an element of the collection"
        objects.contains Util.getRandom(objects)
    }

    def "When getRandom() is called on a singleton, the element is returned"() {
        given: "a singleton collection"
        def object = new Object()
        Collection<Object> objects = [object]

        expect: "the getRandom() returns the object"
        object.is Util.getRandom(objects)
    }

    def "When getRandom() is called on an empty collection, null is returned"() {
        given: "an empty collection"
        Collection<Integer> objects = []

        expect: "the getRandom() returns null"
        null == Util.getRandom(objects)
    }

    def "When getRandom() is called on null, null is returned"() {
        given: "an empty collection"
        Collection<Integer> objects = null

        expect: "the getRandom() returns null"
        null == Util.getRandom(objects)
    }
}
