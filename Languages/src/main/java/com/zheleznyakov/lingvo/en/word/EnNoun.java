package com.zheleznyakov.lingvo.en.word;


import static com.zheleznyakov.lingvo.en.word.EnSpellingHelper.makePossessive;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.en.EnWord;

import com.zheleznyakov.lingvo.basic.FormName;
import com.zheleznyakov.lingvo.basic.MultiFormWord;
import com.zheleznyakov.lingvo.basic.parts.Noun;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.basic.util.WordFormatter;

public class EnNoun extends EnWord implements Noun, MultiFormWord {

    private final String alternativeForm;
    private final Map<FormName, String> irregularForms;

    @Override
    public boolean isRegular() {
        return irregularForms.isEmpty();
    }

    @Override
    public boolean isProperNoun() {
        return false;
    }

    @Override
    @NotNull
    public String[] getForms() {
        return getForms(mainForm, irregularForms);
    }

    @Override
    @NotNull
    public String[] getFormsFull() {
        return this.alternativeForm == null
                ? getForms()
                : joinForms(getForms(), getForms(this.alternativeForm, Word.EMPTY_IRREGULAR_FORMS));
    }

    @NotNull
    private String[] getForms(String form, Map<FormName, String> irregularForms) {
        return WordFormatter.getForms(form, irregularForms, EnNounFormName.values());
    }

    @Override
    public String getForm(FormName formName) {
        return getForm(formName, irregularForms);
    }

    protected EnNoun(Builder builder) {
        super(builder);
        alternativeForm = builder.alternativeForm;
        irregularForms = builder.irregularForms == null
                ? Word.EMPTY_IRREGULAR_FORMS
                : builder.irregularForms;
    }

    public static Builder builder(@NotNull String noun) {
        return new Builder(noun);
    }

    public static EnNoun build(String mainForm) {
        return new EnNoun(EnNoun.builder(mainForm));
    }

    public static class Builder extends EnWord.Builder<EnNoun> {
        private String alternativeForm;
        private boolean properNoun = false;
        private Map<FormName, String> irregularForms;

        private Builder(String mainForm) {
            super(mainForm);
        }

        public EnNoun irregularPlural(String pluralForm) {
            if (irregularForms == null)
                irregularForms = new HashMap<>();
            irregularForms.put(EnNounFormName.NOMINATIVE_PLURAL, pluralForm);
            irregularForms.put(EnNounFormName.POSSESSIVE_PLURAL, makePossessive(pluralForm));
            return build();
        }

        public EnNoun alternativeForm(String alternativeForm) {
            this.alternativeForm = alternativeForm;
            return build();
        }

        public EnNoun properNoun() {
            properNoun = true;
            return build();
        }

        @Override
        public EnNoun build() {
            return properNoun ? new EnProperNoun(this) : new EnNoun(this);
        }
    }

    public enum EnNounFormName implements FormName {
        NOMINATIVE_SINGLE(Function.identity()),
        NOMINATIVE_PLURAL(EnSpellingHelper::appendSEnding),
        POSSESSIVE_SINGLE(EnSpellingHelper::makePossessive),
        POSSESSIVE_PLURAL(NOMINATIVE_PLURAL.standardConverter.andThen(POSSESSIVE_SINGLE.standardConverter));

        private final Function<String, String> standardConverter;

        EnNounFormName(Function<String, String> standardConverter) {
            this.standardConverter = standardConverter;
        }

        @Override
        public boolean isMandatory() {
            return true;
        }

        @Override
        public Function<String, String> getStandardConverter() {
            return standardConverter;
        }

        @Override
        public FormName getRoot() {
            return null;
        }
    }
}
