package com.zheleznyakov.lingvo.language.en;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.basic.util.WordFormatter;

public class EnVerbPhrase extends EnVerb {
    private final String phrasePart;

    EnVerbPhrase(@NotNull EnVerb.Builder verbPartBuilder, String phrasePart) {
        super(verbPartBuilder);
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
        if (alternativeForm == null)
            return getForms();
        else {
            String[] formsFull = WordFormatter.getForms(alternativeForm, irregularForms, EnVerbFormName.values());
            formsFull = appendPhrasePart(formsFull);
            return joinForms(getForms(), formsFull);
        }
    }
}
