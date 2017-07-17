package com.zheleznyakov.lingvo.language.en;

import org.junit.Assert;
import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;

public class TestEnNoun {

    @Test
    public void testCreateEnNoun() {
        String mockNoun = "abc";
        EnNoun noun = EnNoun.builder(mockNoun)
                .build();

        Assert.assertEquals(Language.ENGLISH, noun.getLanguage());
        Assert.assertEquals(PartOfSpeech.NOUN, noun.getPartOfSpeech());
        Assert.assertTrue(noun.isRegular());
        Assert.assertEquals(mockNoun, noun.getMainForm());
    }

    @Test
    public void testNounDeclensions_StandardEnding() {
        EnNoun house = EnNoun.builder("house")
                .build();

        Assert.assertArrayEquals(new String[]{"house", "houses", "house's", "houses'"}, house.getDeclensions());
    }

    @Test
    public void testNounDeclensions_SibilantEnding() {
        EnNoun box = EnNoun.builder("box")
                .build();
        EnNoun sandwich = EnNoun.builder("sandwich")
                .build();
        EnNoun parish = EnNoun.builder("parish")
                .build();
        EnNoun miss = EnNoun.builder("miss")
                .build();

        Assert.assertArrayEquals(new String[]{"box", "boxes", "box's", "boxes'"}, box.getDeclensions());
        Assert.assertArrayEquals(new String[]{"sandwich", "sandwiches", "sandwich's", "sandwiches'"}, sandwich.getDeclensions());
        Assert.assertArrayEquals(new String[]{"parish", "parishes", "parish's", "parishes'"}, parish.getDeclensions());
        Assert.assertArrayEquals(new String[]{"miss", "misses", "miss'", "misses'"}, miss.getDeclensions());
    }

    @Test
    public void testNounDeclensions_YEnding() {
        EnNoun city = EnNoun.builder("city")
                .build();
        EnNoun boy = EnNoun.builder("boy")
                .build();

        Assert.assertArrayEquals(new String[]{"city", "cities", "city's", "cities'"}, city.getDeclensions());
        Assert.assertArrayEquals(new String[]{"boy", "boys", "boy's", "boys'"}, boy.getDeclensions());
    }

    @Test
    public void testNounDeclensions_FEnding() {
        EnNoun thief = EnNoun.builder("thief")
                .build();
        EnNoun wife = EnNoun.builder("wife")
                .build();
        EnNoun cliff = EnNoun.builder("cliff")
                .build();

        Assert.assertArrayEquals(new String[]{"thief", "thieves", "thief's", "thieves'"}, thief.getDeclensions());
        Assert.assertArrayEquals(new String[]{"wife", "wives", "wife's", "wives'"}, wife.getDeclensions());
        Assert.assertArrayEquals(new String[]{"cliff", "cliffs", "cliff's", "cliffs'"}, cliff.getDeclensions());
    }

    @Test
    public void testNounDeclensions_Irregular() {
        EnNoun man = EnNoun.builder("man")
                .irregularPlural("men")
                .build();
        EnNoun hero = EnNoun.builder("hero")
                .irregularPlural("heroes")
                .build();

        Assert.assertFalse(man.isRegular());
        Assert.assertArrayEquals(new String[]{"man", "men", "man's", "men's"}, man.getDeclensions());
        Assert.assertArrayEquals(new String[]{"hero", "heroes", "hero's", "heroes'"}, hero.getDeclensions());
    }
}
