package com.zheleznyakov.lingvo.basic.exercisers

import com.google.common.collect.ImmutableSet
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.Record
import com.zheleznyakov.lingvo.implementations.FakeEnglish
import com.zheleznyakov.lingvo.implementations.FakeExercise
import com.zheleznyakov.lingvo.implementations.FakeWordExerciser
import com.zheleznyakov.lingvo.implementations.TestableMultiFormNoun
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord
import spock.lang.Specification

import java.util.stream.IntStream

class WordExerciserSpec extends Specification {
    private final LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE, "Test dictionary"]
    private WordExerciser<FakeExercise, Answer> exerciser

    def "A newly created exerciser is in the INITIALISED state"() {
        when: "an exerciser is created"
        exerciser = new FakeWordExerciser(dictionary)

        then: "it is in the INITIALISED state"
        this.exerciser.state == WordExerciser.State.INITIALISED
    }

    def "When an exerciser is started on the non-empty dictionary, it is in the STARTED state"() {
        given: "an exerciser"
        exerciser = new FakeWordExerciser(dictionary)

        and: "the non-empty dictionary"
        addRecordsToDictionary(1)

        when: "the exerciser is started"
        exerciser.start()

        then: "it is in the STARTED state"
        exerciser.state == WordExerciser.State.STARTED;
    }

    def "When starting an exerciser on the empty dictionary, it goes directly to STOPPED state"() {
        given: "an exerciser"
        exerciser = [dictionary] as FakeWordExerciser

        expect: "the dictionary to have no records"
        dictionary.records.isEmpty()

        when: "the exerciser is started"
        exerciser.start()

        then: "it is in STOPPED state"
        exerciser.state == WordExerciser.State.STOPPED
    }

    def "When all words have been exercised, the exerciser goes into STOPPED state"() {
        given: "a dictionary containing one record"
        addRecordsToDictionary(1)

        and: "a started exerciser"
        exerciser = [dictionary] as FakeWordExerciser
        exerciser.start()

        expect: "the exerciser has words to learn"
        exerciser.hasNext()

        when: "the next exercise is returned"
        GrammaticalWord word = exerciser.next().word;

        then: "this is the word from the dictionary"
        word == dictionary.records.iterator().next().word

        and: "the exerciser is in the STARTED state"
        exerciser.state == WordExerciser.State.STARTED

        when: "the correct answer is submitted"
        exerciser.submitAnswer(Stub(Answer))

        then: "the exerciser is in the STOPPED state"
        exerciser.state == WordExerciser.State.STOPPED

        and: "the exerciser does not have more words to learn"
        !exerciser.hasNext()
    }

    def "When not all words have been exercised yet, the exerciser still is in STARTED state"() {
        given: "a dictionary containing two records"
        addRecordsToDictionary(2)

        and: "a started exerciser"
        exerciser = [dictionary] as FakeWordExerciser
        exerciser.start()

        when: "one word is exercised"
        exerciseWords(1)

        then: "the exerciser still has more words to learn"
        exerciser.hasNext()

        and: "the exerciser is in STARTED state"
        exerciser.state == WordExerciser.State.STARTED
    }

    def "When trying to start an exerciser in STARTED state, throw"() {
        given: "a started exerciser"
        addRecordsToDictionary(1)
        exerciser = [dictionary] as FakeWordExerciser
        exerciser.start()

        expect: "it to be in STARTED state"
        exerciser.state == WordExerciser.State.STARTED

        when: "trying it to start the second time"
        exerciser.start()

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to start: the exerciser has state [${WordExerciser.State.STARTED}], expected [${WordExerciser.State.INITIALISED}]"
    }

    def "When trying to start an exerciser in STOPPED state, throw"() {
        given: "an exerciser"
        exerciser = [dictionary] as FakeWordExerciser
        exerciser.start()

        expect: "it to be in STOPPED state"
        exerciser.state == WordExerciser.State.STOPPED

        when: "trying to start the exerciser"
        exerciser.start()

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to start: the exerciser has state [${WordExerciser.State.STOPPED}], expected [${WordExerciser.State.INITIALISED}]"
    }

    def "When checking whether there are next words to learn while in INITIALISED state, return true"() {
        given: "an exerciser"
        exerciser = [dictionary] as FakeWordExerciser

        expect: "it to be in INITIALISED state"
        exerciser.state == WordExerciser.State.INITIALISED

        when: "checking if it has next word to learn"
        boolean hasNext = exerciser.hasNext()

        then: "then true is returned"
        hasNext
    }

    def "When trying to get the next word while in INITIALISED state, throw"() {
        given: "an exerciser"
        exerciser = [dictionary] as FakeWordExerciser

        expect: "it to be in INITIALISED state"
        exerciser.state == WordExerciser.State.INITIALISED

        when: "trying to get the next word"
        exerciser.next()

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to start next exercise: the exerciser has state [${WordExerciser.State.INITIALISED}], expected [${WordExerciser.State.STARTED}]"
    }

    def "When trying to get the next word while in STOPPED state, throw"() {
        given: "a stopped exerciser"
        exerciser = [dictionary] as FakeWordExerciser
        exerciser.start()

        expect: "it to be in STOPPED state"
        exerciser.state == WordExerciser.State.STOPPED

        when: "trying to get the next word to exercise"
        exerciser.next()

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to start next exercise: the exerciser has state [${WordExerciser.State.STOPPED}], expected [${WordExerciser.State.STARTED}]"
    }

    def "When trying to submit an answer while in INITIALISED state, throw"() {
        given: "an exerciser"
        exerciser = [dictionary] as FakeWordExerciser

        expect: "it is in INITIALISED state"
        exerciser.state == WordExerciser.State.INITIALISED

        when: "trying to submit an answer"
        exerciser.submitAnswer(Stub(Answer))

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to submit the answer: the exerciser has state [${WordExerciser.State.INITIALISED}], expected [${WordExerciser.State.STARTED}]"
    }

    def "When trying to submit an answer while in STOPPED state, throw"() {
        given: "a stopped exerciser"
        exerciser = [dictionary] as FakeWordExerciser
        exerciser.start()

        expect: "it to be in STOPPED state"
        exerciser.state == WordExerciser.State.STOPPED

        when: "trying to submit an answer"
        exerciser.submitAnswer(Stub(Answer))

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to submit the answer: the exerciser has state [${WordExerciser.State.STOPPED}], expected [${WordExerciser.State.STARTED}]"
    }

    def "When trying to get the next word two times in a row, throw"() {
        given: "a dictionary with two records"
        addRecordsToDictionary(2)

        and: "a started exerciser"
        exerciser = [dictionary] as FakeWordExerciser
        exerciser.start()

        when: "trying to get the next word twice in a row"
        exerciser.next()
        exerciser.next()

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Cannot go to the next exercise: please submit the answer first"
    }

    def "When trying to submit an answer right after starting the exerciser, throw"() {
        given: "a non-empty dictionary"
        addRecordsToDictionary(1)

        and: "a started exerciser"
        exerciser = [dictionary] as FakeWordExerciser
        exerciser.start()

        when: "trying to submit an answer"
        exerciser.submitAnswer(Stub(Answer))

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Cannot submit an answer: please start the new exercise first"
    }

    def "When trying to submit an answer two times in a row, throw"() {
        given: "a non-empty dictionary"
        addRecordsToDictionary(1)

        and: "a started exerciser"
        exerciser = [dictionary] as FakeWordExerciser
        exerciser.start()

        when: "the next exercise is got and two answers in a row are submitted"
        def next = exerciser.next()
        exerciser.submitAnswer(Stub(Answer))
        exerciser.submitAnswer(Stub(Answer))

        then: "an ExerciseException is thrown"
        thrown(ExerciseException)
    }

    def "The exerciser goes through the records in arbitrary order"() {
        given: "the dictionary has 1000 records"
        addRecordsToDictionary(50)

        when: "exercising the dictionary 100 times"
        def numberOfDistinctOrders = IntStream.range(0, 100)
                .mapToObj() { getExercisingOrder() }
                .collect(ImmutableSet.toImmutableSet())
                .size()

        then: "the number of distinct orders is close to 100"
        numberOfDistinctOrders > 95
    }

    private def addRecordsToDictionary(int numberOfRecords) {
        IntStream.range(0, numberOfRecords)
                .forEach { addRecord(it) }
        assert dictionary.records.size() == numberOfRecords
    }

    private def addRecord(def num) {
        char suffix = num + (char)'a'
        GrammaticalWord word = new TestableMultiFormNoun("word${suffix}")
        dictionary.record(word, "meaning${suffix}").add()
    }

    private def exerciseWords(int numberOfWords) {
        IntStream.range(0, numberOfWords)
                .forEach { exerciseWord() }
    }

    private exerciseWord() {
        exerciser.next()
        exerciser.submitAnswer(Stub(Answer))
    }


    private List<Record> getExercisingOrder() {
        exerciser = [dictionary] as FakeWordExerciser
        exerciser.start()
        List<Record> exercisingOrder = []
        while (exerciser.hasNext()) {
            exercisingOrder += [exerciser.next()]
            exerciser.submitAnswer(Stub(Answer))
        }
        return exercisingOrder
    }
}
