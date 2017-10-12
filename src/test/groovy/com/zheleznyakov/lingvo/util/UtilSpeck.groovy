package com.zheleznyakov.lingvo.util

import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Function

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
        pattern                      | arguments                || expectedMessage
        null                         | ["a", false].toArray()   || null
        ""                           | ["argument"].toArray()   || ""
        "No arguments"               | [].toArray()             || "No arguments"
        "Missing argument {}"        | [].toArray()             || "Missing argument "
        "true={}"                    | [true].toArray()         || "true=true"
        "{}=true"                    | [true].toArray()         || "true=true"
        "one {}, two {}"             | [1, 2].toArray()         || "one 1, two 2"
        "Too many arguments: {}"     | ["one", "two"].toArray() || "Too many arguments: one"
        "Too few arguments: {}, {}"  | [1d].toArray()           || "Too few arguments: 1.0, "
        "Passed argument is null {}" | null                     || "Passed argument is null "
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

    @Unroll
    def "When trying to find max element in array=#array, throw"(double[] array) {
        when: "an illegal array is passed"
        Util.max(array)

        then: "IllegalArgumentException is thrown"
        thrown(IllegalArgumentException)

        where: "arrays are"
        array | _
        null  | _
        []    | _
    }

    @Unroll
    def "Max element in #array of doubles is #expectedMax"(double[] array, double expectedMax) {
        expect: "the max element in the array to be found correctly"
        Util.max(array) == expectedMax

        where: "the parameters are"
        array     | expectedMax
        [1]       | 1
        [1, 2]    | 2
        [2, 1]    | 2
        [1, 2, 3] | 3
        [3, 2, 1] | 3
        [1, 3, 2] | 3
    }

    @Unroll
    def "When trying to find max element in object array=#array, throw"(Integer[] array) {
        when: "an illegal array is passed"
        Util.max(new IntegerToDouble(), array)

        then: "IllegalArgumentException is thrown"
        thrown(IllegalArgumentException)

        where: "arrays are"
        array | _
        null  | _
        []    | _
    }

    @Unroll
    def "Max element in #array is #expectedMax"(Integer[] array, double expectedMax) {
        expect: "the max element in the array to be found correctly"
        Util.max(new IntegerToDouble(), array) == expectedMax

        where: "the parameters are"
        array     | expectedMax
        [1]       | 1
        [2, 1, 3] | 3
    }

    @Unroll
    def "Validate #boolExpression expression and runnable wasRun=#wasRun"() {
        given: "a runnable"
        UncheckedRunnable runnable = new TestRunnable()

        when: "running expression"
        Util.validateExpression(boolExpression, runnable)

        then: "the runnable was run if necessary"
        runnable.wasRun == wasRun

        where: "parameters are"
        boolExpression      || wasRun
        "abc".contains('b') || false
        "abc".contains('d') || true

    }

    private class IntegerToDouble implements Function<Integer, Double> {
        @Override
        Double apply(Integer integer) {
            return integer.doubleValue();
        }
    }

    private class TestRunnable implements UncheckedRunnable<Exception> {
        boolean wasRun = false;

        @Override
        void run() {
            wasRun = true;
        }
    }
}
