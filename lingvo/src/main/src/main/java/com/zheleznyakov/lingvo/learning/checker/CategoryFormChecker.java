package com.zheleznyakov.lingvo.learning.checker;

import com.zheleznyakov.lingvo.basic.Category;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.learning.LearningDictionary;

public class CategoryFormChecker {

    private final LearningDictionary dictionary;

    public CategoryFormChecker(LearningDictionary dictionary) {
        this.dictionary = dictionary;
    }

    public boolean exerciseInCategory(Category category, Word word, String... forms) {
        return true;
    }
}
