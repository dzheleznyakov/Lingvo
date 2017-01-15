package com.zheleznyakov.lingvo.vocabulary;

public interface Vocabulary {
    public final static Vocabulary NULL_VOCABULARY = null;
    
    public void addWord(Word word);
    
    public Word getWord(String s);

}