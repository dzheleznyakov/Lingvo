package com.zheleznyakov.lingvo.language.en;

import org.junit.Assert;
import org.junit.Test;

import com.zheleznyakov.lingvo.util.PartOfSpeech;

public class TestEnNoun {

    @Test
    public void testEnNounCreation() {
        String mockNoun = "abc";
        EnNoun noun = EnNoun.builder(mockNoun)
                .build();

        Assert.assertEquals(Language.ENGLISH, noun.getLanguage());
        Assert.assertEquals(PartOfSpeech.NOUN, noun.getPartOfSpeech());
        Assert.assertEquals(true, noun.isRegular());
        Assert.assertEquals(mockNoun, noun.getMainForm());

        noun = EnNoun.builder(mockNoun)
                .irregular()
                .build();
        Assert.assertEquals(false, noun.isRegular());
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

        Assert.assertArrayEquals(new String[]{"city", "cities", "city's", "cities'"}, city.getDeclensions());
    }
}
