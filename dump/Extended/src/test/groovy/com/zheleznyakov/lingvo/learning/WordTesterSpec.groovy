package com.zheleznyakov.lingvo.learning

import com.zheleznyakov.lingvo.basic.Language
import com.zheleznyakov.lingvo.basic.Word
import com.zheleznyakov.lingvo.en.word.EnNoun
import com.zheleznyakov.lingvo.en.word.EnVerb
import com.zheleznyakov.lingvo.learning.WordTester.Mode
import spock.lang.Specification
import spock.lang.Unroll

import static com.zheleznyakov.lingvo.learning.WordTester.Mode.*

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
            testWordsInDictionary(wordsToMeanings, mode, true, true)

        then: "all words have correct statistics in the dictionary"
        assertWordsCount(wordsToMeanings.keySet(), numberOfTests)
        areAllWordsLearned(wordsToMeanings.keySet()) == allWordsLearned

        where: "parameters are"
        mode     | numberOfTests    || allWordsLearned
        FORWARD  | 1                || false
        FORWARD  | maxLearningCount || true
        BACKWARD | 1                || false
        BACKWARD | maxLearningCount || true
    }

    def "Test a dictionary in a TOGGLE mode"() {
        given: "a dictionary with several words"
        Map<Word, String> wordsToMeanings = addWordsToDictionaryAndReturnEntries(10)

        and: "a tester in TOGGLE mode"
        tester.mode = TOGGLE

        when: "all words are tested correctly"
        testWordsInDictionary(wordsToMeanings, TOGGLE, true, true)

        then: "all the words have learning count equal to 1"
        assertWordsCount(wordsToMeanings.keySet(), 1)
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

    def "When starting a tester second time, throw"() {
        given: "a dictionary with several words"
        addWordsToDictionaryAndReturnEntries(10)

        when: "starting the tester two times"
        tester.start()
        tester.start()

        then: "a IllegalStateException is thrown"
        thrown(IllegalStateException)
    }

    def "When switching mode of a started tester, throw"() {
        given: "a started tester in strict regime"
        addWordsToDictionaryAndReturnEntries(1)
        tester.mode = FORWARD
        tester.start()

        when: "trying to change the regime"
        tester.mode = BACKWARD

        then: "an IllegalStateException is thrown"
        thrown(IllegalStateException)
    }

    def "When testing a dictionary, only non-learned words are shown"() {
        given: "a dictionary with several words"
        Map<Word, String> wordsToMeanings = addWordsToDictionaryAndReturnEntries(10)

        and: "all words are tested correctly"
        for (int i = 0; i < maxLearningCount; i++)
            testWordsInDictionary(wordsToMeanings, tester.mode, true, true)

        and: "a new word is added to the dictionary"
        EnVerb newWord = EnVerb.build("test")
        dictionary.add(newWord, "test");

        when: "starting the tester again"
        tester.start()

        then: "the next word is NO_WORD"
        newWord == tester.nextWord
        null == tester.nextWord
    }

    @Unroll
    def "Test a dictionary in non-strict regime in #mode mode"() {
        given: "a dictionary in a non-strict regime"
        Map<Word, String> wordsToMeaning = addWordsToDictionaryAndReturnEntries(10)
        tester.setMode(mode)
        tester.setStrict(false)

        when: "all words are tested with partial answers"
        testWordsInDictionary(wordsToMeaning, mode, true, false)

        then: "all the words have learning count equal to 1"
        assertWordsCount(wordsToMeaning.keySet(), 1)

        where: "the modes are"
        mode     | _
        FORWARD  | _
        BACKWARD | _
    }

    def "When switching regime of a started tester, throw"() {
        given: "a started tester in strict regime"
        addWordsToDictionaryAndReturnEntries(1)
        tester.strict = true
        tester.start()

        when: "trying to change the regime"
        tester.strict = false

        then: "an IllegalStateException is thrown"
        thrown(IllegalStateException)
    }

    private addWordsToDictionaryAndReturnEntries(int number) {
        Map<Word, String> entries = [:]
        for (int i = 0; i < 10; i++)
            entries[getWord(i)] = getMeaning(i)
        entries.each { entry -> dictionary.add(entry.key, entry.value) }
        return entries
    }

    private getTestedWordsInOrderTheyRan() {
        List<Word> wordsInOrder = []
        tester.start()
        while (tester.hasNext())
            wordsInOrder << tester.nextWord
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
        testWordsInDictionary(wordsToMeanings, FORWARD, correctly, true)
    }

    private testWordsInDictionary(Map<Word, String> wordsToMeanings, Mode mode, boolean correctly, boolean strictly) {
        tester.start()
        Mode modeForNextWord = mode == TOGGLE ? FORWARD : mode
        while (tester.hasNext()) {
            tester.test getAnswer(wordsToMeanings, modeForNextWord, strictly) + (correctly ? "" : "a")
            modeForNextWord = mode == TOGGLE ? toggleMode(modeForNextWord) : mode
        }
    }

    private String getAnswer(Map<Word, String> wordStringMap, Mode mode, boolean strictly) {
        return mode == FORWARD ?
                prepareAnswer(wordStringMap[tester.nextWord], strictly) :
                prepareAnswer(tester.nextWord.mainForm, strictly)
    }

    private String prepareAnswer(String fullAnswer, boolean strictly) {
        def halveWord = { it.substring(0, it.length() / 2 as Integer) }
        return strictly ? fullAnswer : halveWord.call(fullAnswer)
    }

    private toggleMode(Mode mode) {
        mode == FORWARD ? BACKWARD : FORWARD
    }

    private assertWordsCount(words, count) {
        words.each { assert dictionary.getCount(it) == count }
    }

    private boolean areAllWordsLearned(Collection<Word> words) {
        boolean result = false
        words.each { result = result || dictionary.isLearned(it) }
        return result
    }
}
