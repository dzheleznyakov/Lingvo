package com.zheleznyakov.lingvo.dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.Language;
import com.zheleznyakov.lingvo.util.Util;

public class Dictionary {

    private final Language language;
    private Map<Word, String> words = new HashMap<>();

    public Dictionary(Language language) {
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }

    public void add(Word word, String meaning) {
        Util.validateArgument(language == word.getLanguage(),
                "Cannot add {} word to {} dictionary", word.getLanguage(), language);
        words.put(word, meaning);
    }

    public boolean contains(Word word) {
        return words.containsKey(word);
    }

    public String getMeaning(Word word) {
        return words.get(word);
    }

    public Map<Word, String> asMap() {
        return new HashMap<>(words);
    }

    public Set<Word> getWords(String mainForm) {
        return getWords(word -> Objects.equals(mainForm, word.getMainForm()));
    }

    public Set<Word> getWords(PartOfSpeech partOfSpeech) {
        return getWords(word -> partOfSpeech == word.getPartOfSpeech());

    }

    private Set<Word> getWords(Predicate<Word> wordFilter) {
        return words.keySet()
                .stream()
                .filter(wordFilter)
                .collect(Collectors.toSet());
    }
}
