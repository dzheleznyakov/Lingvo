package com.zheleznyakov.lingvo.language.en;

import static com.zheleznyakov.testutils.ZhAssert.assertWordForms;
import static com.zheleznyakov.testutils.ZhAssert.assertWordFormsFull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zheleznyakov.lingvo.basic.PartOfSpeech;
import com.zheleznyakov.lingvo.language.Language;

public class EnArticleTest {
    @Test
    public void testAnArticle_The() {
        String mainForm = "the";
        EnArticle the = EnArticle.build(mainForm);
        String[] expectedForms = {mainForm};

        assertEquals(Language.ENGLISH, the.getLanguage());
        assertEquals(PartOfSpeech.ARTICLE, the.getPartOfSpeech());
        assertEquals(mainForm, the.getMainForm());
        assertWordForms(the, expectedForms);
        assertWordFormsFull(the, expectedForms);
    }

    @Test
    public void testEnArticle_A() {
        String mainForm = "a";
        EnArticle a = EnArticle.build(mainForm);

        assertWordFormsFull(a, "a/an");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createIllegalEnArticle() {
        EnArticle.build("abc");
    }
}
