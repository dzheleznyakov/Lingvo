package com.zheleznyakov.lingvo.basic.dictionary

import com.google.common.collect.ImmutableMap
import com.zheleznyakov.lingvo.basic.implementations.FakeEnglish
import com.zheleznyakov.lingvo.basic.implementations.TestableMultiFormWord
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord
import spock.lang.Specification

import java.util.stream.IntStream

class WordLearnerSpec extends Specification {
    private final LearningDictionary dictionary = [FakeEnglish.FIXED_LANGUAGE]
    private Map<GrammaticalWord, String> wordsToDescriptions

    /*
    - Learn in different modes (forward, backward, toggle)
    - Learn only those words, that are not completed
    - Check statistics (percentage)
    - Configure (set learned count)

    - Word learner
        (=) needs to be started
        (=) works then in an interactive mode
        (=) starts with out-state, returning a word from the dictionary (from a record)
        (=) then goes into in-state, expecting a string (description)
        = checks the answer and update the statistics of the record in the dictionary accordingly
        (=) then goes into in-state and return the next word from the dictionary
        (=) and it goes on like this until it through with the words
        (=) after it finishes, it stops automatically
        = it goes through the words in an arbitrary order
     */

    def "A newly created word learner is in the INITIALISED state"() {
        when: "a word learner is created"
        WordLearner learner = new WordLearner(dictionary)

        then: "it is in the INITIALISED state"
        learner.state == WordLearner.State.INITIALISED
    }

    def "When a learner is started on the non-empty dictionary, it is in the STARTED state"() {
        given: "a word learner"
        WordLearner learner = new WordLearner(dictionary)

        and: "the non-empty dictionary"
        addRecordsToDictionary(1)

        when: "the learner is started"
        learner.start()

        then: "it is in the STARTED state"
        learner.state == WordLearner.State.STARTED;
    }

    def "When starting a learner on the empty dictionary, it goes directly to STOPPED state"() {
        given: "a word learner"
        WordLearner learner = [dictionary]

        expect: "the dictionary to have no records"
        dictionary.records.isEmpty()

        when: "the word learner is started"
        learner.start()

        then: "it is in STOPPED state"
        learner.state == WordLearner.State.STOPPED
    }

    def "When all words have been exercised, the learner goes into STOPPED state"() {
        given: "a dictionary containing one record"
        addRecordsToDictionary(1)

        and: "a started word learner"
        WordLearner learner = [dictionary]
        learner.start()

        expect: "the learner has words to learn"
        learner.hasNext()

        when: "the learner returns a question"
        GrammaticalWord word = learner.next();

        then: "this is the word from the dictionary"
        word == dictionary.records.iterator().next().word

        and: "the learner is in the STARTED state"
        learner.state == WordLearner.State.STARTED

        when: "the correct answer is submitted"
        learner.submitAnswer(wordsToDescriptions[word])

        then: "the learner is in the STOPPED state"
        learner.state == WordLearner.State.STOPPED

        and: "the learner does not have more words to learn"
        !learner.hasNext()
    }

    def "When not all words have been exercised yet, the learner still is in STARTED state"() {
        given: "a dictionary containing two records"
        addRecordsToDictionary(2)

        and: "a started word learner"
        WordLearner learner = [dictionary]
        learner.start()

        when: "one word is exercised"
        exerciseWords(learner, 1)

        then: "the learner still has more words to learn"
        learner.hasNext()

        and: "the learner is in STARTED state"
        learner.state == WordLearner.State.STARTED
    }

    def "When trying to start a learner in STARTED state, throw"() {
        given: "a started word learner"
        addRecordsToDictionary(1)
        WordLearner learner = [dictionary]
        learner.start()

        expect: "it to be in STARTED state"
        learner.state == WordLearner.State.STARTED

        when: "trying it to start the second time"
        learner.start()

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to start: the learner has state [${WordLearner.State.STARTED}], expected [${WordLearner.State.INITIALISED}]"
    }

    def "When trying to start a learner in STOPPED state, throw"() {
        given: "a word learner"
        WordLearner learner = [dictionary]
        learner.start()

        expect: "it to be in STOPPED state"
        learner.state == WordLearner.State.STOPPED

        when: "trying to start the learner"
        learner.start()

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to start: the learner has state [${WordLearner.State.STOPPED}], expected [${WordLearner.State.INITIALISED}]"
    }

    def "When checking whether there are next words to learn while in INITIALISED state, return true"() {
        given: "a word learner"
        WordLearner learner = [dictionary]

        expect: "it to be in INITIALISED state"
        learner.state == WordLearner.State.INITIALISED

        when: "checking if it has next word to learn"
        boolean hasNext = learner.hasNext()

        then: "then true is returned"
        hasNext
    }

    def "When trying to get the next word while in INITIALISED state, throw"() {
        given: "a word learner"
        WordLearner learner = [dictionary]

        expect: "it to be in INITIALISED state"
        learner.state == WordLearner.State.INITIALISED

        when: "trying to get the next word"
        learner.next()

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to start next exercise: the learner has state [${WordLearner.State.INITIALISED}], expected [${WordLearner.State.STARTED}]"
    }

    def "When trying to get the next word while in STOPPED state, throw"() {
        given: "a stopped word learner"
        WordLearner learner = [dictionary]
        learner.start()

        expect: "it to be in STOPPED state"
        learner.state == WordLearner.State.STOPPED

        when: "trying to get the next word to exercise"
        learner.next()

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to start next exercise: the learner has state [${WordLearner.State.STOPPED}], expected [${WordLearner.State.STARTED}]"
    }

    def "When trying to submit an answer while in INITIALISED state, throw"() {
        given: "a word learner"
        WordLearner learner = [dictionary]

        expect: "it is in INITIALISED state"
        learner.state == WordLearner.State.INITIALISED

        when: "trying to submit an answer"
        learner.submitAnswer("some answer")

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to submit the answer: the learner has state [${WordLearner.State.INITIALISED}], expected [${WordLearner.State.STARTED}]"
    }

    def "When trying to submit an answer while in STOPPED state, throw"() {
        given: "a stopped word learner"
        WordLearner learner = [dictionary]
        learner.start()

        expect: "it to be in STOPPED state"
        learner.state == WordLearner.State.STOPPED

        when: "trying to submit an answer"
        learner.submitAnswer("some answer")

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Failed to submit the answer: the learner has state [${WordLearner.State.STOPPED}], expected [${WordLearner.State.STARTED}]"
    }

    def "When trying to get the next word two times in a row, throw"() {
        given: "a dictionary with two records"
        addRecordsToDictionary(2)

        and: "a started word learner"
        WordLearner learner = [dictionary]
        learner.start()

        when: "trying to get the next word twice in a row"
        learner.next()
        learner.next()

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Cannot go to the next exercise: please submit the answer first"
    }

    def "When trying to submit an answer right after starting the dictionary, throw"() {
        given: "a non-empty dictionary"
        addRecordsToDictionary(1)

        and: "a started word learner"
        WordLearner learner = [dictionary]
        learner.start()

        when: "trying to submit an answer"
        learner.submitAnswer("some answer")

        then: "an ExerciseException is thrown"
        def e = thrown(ExerciseException)
        e.message == "Cannot submit an answer: please start the new exercise first"
    }

    def "When trying to submit an answer two ties in a row, throw"() {
        given: "a non-empty dictionary"
        addRecordsToDictionary(1)

        and: "a started word learner"
        WordLearner learner = [dictionary]
        learner.start()

        when: "the next exercise is got and two answers in a row are submitted"
        def next = learner.next()
        learner.submitAnswer("some answer")
        learner.submitAnswer("another answer")

        then: "an ExerciseException is thrown"
        thrown(ExerciseException)
    }

    def "Exercising ten words"() {
        given: "a dictionary with ten records"
        addRecordsToDictionary(10)

        and: "a started word learner"
        WordLearner learner = [dictionary]
        learner.start()

        when: "all words are exercised"
        exerciseWords(learner, 10)

        then: "the learner has not more words to exercise"
        !learner.hasNext()

        and: "it is in STOPPED state"
        learner.state == WordLearner.State.STOPPED
    }

    private def addRecordsToDictionary(int numberOfRecords) {
        IntStream.range(0, numberOfRecords)
                .forEach { addRecord(it) }
        assert dictionary.records.size() == numberOfRecords
        wordsToDescriptions = dictionary.records.stream()
                .collect(ImmutableMap.toImmutableMap( { it.word }, { it.description } ))
    }

    private def addRecord(def num) {
        char suffix = num + (char)'a'
        GrammaticalWord word = new TestableMultiFormWord("word${suffix}")
        dictionary.record(word, "meaning${suffix}").add()
    }

    private def exerciseWords(WordLearner wordLearner, int numberOfWords) {
        IntStream.range(0, numberOfWords)
                .forEach { exerciseOneWord(wordLearner) }
    }

    private def exerciseOneWord(WordLearner wordLearner) {
        def answer = wordsToDescriptions[wordLearner.next()]
        wordLearner.submitAnswer(answer)
    }
}
