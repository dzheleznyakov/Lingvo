package com.zheleznyakov.lingvo.en.word;


import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.basic.words.util.WordFormatter;
import com.zheleznyakov.lingvo.en.EnWord;

public class EnVerbPhrase extends EnVerb {
    private final String phrasePart;

    EnVerbPhrase(@NotNull Builder verbPartBuilder, String phrasePart) {
        super(verbPartBuilder);
        verifyChars(phrasePart);
        this.phrasePart = phrasePart;
    }

    @Override
    public String[] getForms() {
        return appendPhrasePart(super.getForms());
    }

    @NotNull
    private String[] appendPhrasePart(String[] forms) {
        for (int i = 0; i < forms.length; i++)
            forms[i] += " " + phrasePart;
        return forms;
    }

    @Override
    public String[] getFormsFull() {
        return alternativeForm == null
            ? getForms()
            : EnWord.joinForms(getForms(), getAlternativeForms());
    }

    @NotNull
    private String[] getAlternativeForms() {
        String[] formsFull = WordFormatter.getForms(alternativeForm, irregularForms, EnVerbFormName.values());
        return appendPhrasePart(formsFull);
    }
}
