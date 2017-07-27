package com.zheleznyakov.lingvo.dictionary;

import java.util.HashSet;
import java.util.Set;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.Language;

public class Dictionary {
    private final Set<Word> words = new HashSet<>();

    private final Language language;

    public Dictionary(Language language) {
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }

    public boolean add(Word word) {
        return words.add(word);
    }

    public int size() {
        return words.size();
    }

    public boolean remove(Word word) {
        return words.remove(word);
    }

    public boolean contains(Word word) {
        return words.contains(word);
    }
}
