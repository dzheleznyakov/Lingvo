package com.zheleznyakov.lingvo.language.en;

import com.zheleznyakov.lingvo.basic.Article;

public class EnArticle extends EnWord implements Article {
    private static final String THE = "the";
    private static final String A = "a";

    private EnArticle(String mainForm) {
        super(mainForm);
    }

    public static EnArticle build(String mainForm) {
        mainForm = mainForm.toLowerCase();
        if (THE.equals(mainForm) || A.equals(mainForm))
            return new EnArticle(mainForm);
        throw new IllegalArgumentException("There is no English article " + mainForm);
    }

    @Override
    public String[] getFormsFull() {
        if (THE.equals(mainForm))
            return getForms();
        else
            return new String[]{"a/an"};
    }
}
