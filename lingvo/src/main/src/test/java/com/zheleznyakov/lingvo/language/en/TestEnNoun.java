package com.zheleznyakov.lingvo.language.en;

import org.junit.Assert;
import org.junit.Test;

import com.zheleznyakov.lingvo.util.ChangeType;
import com.zheleznyakov.lingvo.util.PartOfSpeech;

public class TestEnNoun {

    @Test
    public void testEnNounCreation() {
        String mockNoun = "abc";
        EnNoun noun = EnNoun.builder(mockNoun)
                .build();

        Assert.assertEquals(Language.ENGLISH, noun.getLanguage());
        Assert.assertEquals(PartOfSpeech.NOUN, noun.getPartOfSpeech());
        Assert.assertEquals(ChangeType.REG, noun.getChangeType());
        Assert.assertEquals(mockNoun, noun.getMainForm());

        noun = EnNoun.builder(mockNoun)
                .irregular()
                .build();
        Assert.assertEquals(ChangeType.IRR, noun.getChangeType());
    }

    @Test
    public void testNounDeclensions_StandardEnding() {
        EnNoun house = EnNoun.builder("house")
                .build();

        Assert.assertArrayEquals(new String[]{"house", "houses", "house's", "houses'"}, house.getDeclensions());
    }

    @Test
    public void testNounDeclensions_XEnding() {
        EnNoun box = EnNoun.builder("box")
                .build();

        Assert.assertArrayEquals(new String[]{"box", "boxes", "box's", "boxes'"}, box.getDeclensions());
    }
}
