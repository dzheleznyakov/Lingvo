package com.zheleznyakov.lingvo.learning.checker;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.language.en.EnNoun;
import com.zheleznyakov.lingvo.language.en.categories.EnNounCategory;
import com.zheleznyakov.lingvo.learning.LearningBaseTest;

public class CategoryFormCheckerTest extends LearningBaseTest {

    private Word house = EnNoun.build("house");
    private CategoryFormChecker categoryFormChecker;


    @Before
    public void setUp() throws Exception {
        dictionary.add(house, "дом");
        categoryFormChecker = new CategoryFormChecker(dictionary);
    }

    @Test
    public void checkNounForms_NominativeCase() {
        assertTrue(categoryFormChecker.exerciseInCategory(EnNounCategory.NOMINATIVE_CASE, house, "house", "houses"));
    }
}
