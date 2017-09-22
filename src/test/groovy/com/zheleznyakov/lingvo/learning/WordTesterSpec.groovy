package com.zheleznyakov.lingvo.learning

import com.zheleznyakov.lingvo.basic.Word
import com.zheleznyakov.lingvo.language.Language
import com.zheleznyakov.lingvo.language.en.word.EnNoun
import spock.lang.Specification
import spock.lang.Unroll

import static com.zheleznyakov.lingvo.learning.WordTester.Mode.BACKWARD
import static com.zheleznyakov.lingvo.learning.WordTester.Mode.FORWARD

class WordTesterSpec extends Specification {
    static final int maxLearningCount = 10

    LearningDictionary dictionary
    WordTester tester

    void setup() {
        dictionary = new LearningDictionary(Language.ENGLISH)
        dictionary.maxLearningCount = maxLearningCount
        tester = new WordTester(dictionary)
    }

    def "When starting to test a dictionary, the words are shuffled"() {
        given: "a dictionary with several words"
        Map<Word, String> wordsToMeanings = addWordsToDictionaryAndReturnEntries(20)

        and: "word order on the first run"
        List<Word> wordsInOrder1 = getTestedWordsInOrderTheyRan()

        when: "the dictionary is run the second time"
        List<Word> wordsInOrder2 = getTestedWordsInOrderTheyRan()

        then: "the word order is different"
        wordsInOrder1 != wordsInOrder2
    }

    @Unroll
    def "Test dictionary in #mode mode #numberOfTests time(s)"() {
        given: "a dictionary with several words"
        Map<Word, String> wordsToMeanings = addWordsToDictionaryAndReturnEntries(10)

        and: "a testing mode is set"
        tester.mode = mode

        expect: "that all words have learning count 0"
        assertWordsCount(wordsToMeanings.keySet(), 0)

        when: "all words are tested correctly"
        for (int i = 0; i < numberOfTests; i++)
            testWordsInDictionary(wordsToMeanings, mode, true)

        then: "all words have learning count 1"
        assertWordsCount(wordsToMeanings.keySet(), numberOfTests)
        checkWordsLearned(wordsToMeanings.keySet()) == allWordsLearned

        where: "parameters are"
        mode     | numberOfTests    || allWordsLearned
        FORWARD  | 1                || false
        FORWARD  | maxLearningCount || true
        BACKWARD | 1                || false
        BACKWARD | maxLearningCount || true
    }

    @Unroll
    def "Test learning word pattern -- +#successfulStreak -#failingStreak"() {
        given: "a dictionary with several words"
        Map<Word, String> wordsToMeanings = addWordsToDictionaryAndReturnEntries(10)

        when: "the dictionary is tested according to pattern"
        for (int i = 0; i < successfulStreak; i++)
            testWordsInDictionary(wordsToMeanings, true)
        for (int i = 0; i < failingStreak; i++)
            testWordsInDictionary(wordsToMeanings, false)

        then: "the statistics has correct values"
        assertWordsCount(wordsToMeanings.keySet(), expectedCount)

        where: "the exercise patterns are"
        successfulStreak | failingStreak || expectedCount
        0                | 0             || 0
        10               | 0             || 10
        5                | 1             || 5
        5                | 2             || 4
        5                | 3             || 3
        0                | 10            || 0
    }

    def "When switching the tester mode in the middle of testing, throw"() {
        given: "a dictionary with several words"
        addWordsToDictionaryAndReturnEntries(10)

        when: "starting the tester two times"
        tester.start()
        tester.start()

        then: "a IllegalStateException is thrown"
        thrown(IllegalStateException)
    }

    private addWordsToDictionaryAndReturnEntries(int number) {
        Map<Word, String> entries = new HashMap<>()
        for (int i = 0; i < 10; i++)
            entries.put(getWord(i), getMeaning(i))
        entries.each { entry -> dictionary.add(entry.key, entry.value) }
        return entries
    }

    private getTestedWordsInOrderTheyRan() {
        List<Word> wordsInOrder = []
        tester.start()
        while (tester.hasNext())
            wordsInOrder.add tester.nextWord
        return wordsInOrder
    }


    private getWord(int i) {
        EnNoun.build("word" + getChar(i))
    }

    private getChar(int i) {
        (char) ((int) 'a' + i)
    }


    private getMeaning(int i) {
        "meaning" + getChar(i)
    }

    private testWordsInDictionary(Map<Word, String> wordsToMeanings, boolean correctly) {
        testWordsInDictionary(wordsToMeanings, WordTester.Mode.FORWARD, correctly)
    }

    private testWordsInDictionary(Map<Word, String> wordsToMeanings, WordTester.Mode mode, boolean correctly) {
        tester.start()
        while (tester.hasNext())
            tester.test getAnswer(wordsToMeanings, mode) + (correctly ? "" : "a")
    }

    private String getAnswer(Map<Word, String> wordStringMap, WordTester.Mode mode) {
        if (mode == FORWARD)
            return wordStringMap[tester.nextWord]
        else if (mode == BACKWARD)
            return tester.nextWord.mainForm
    }

    private assertWordsCount(Collection<Word> words, count) {
        words.each { assert dictionary.getCount(it) == count }
    }
    private boolean checkWordsLearned(Collection<Word> words) {
        boolean result = false
        words.each { result = result || dictionary.isLearned(it) }
        return result
    }
}
