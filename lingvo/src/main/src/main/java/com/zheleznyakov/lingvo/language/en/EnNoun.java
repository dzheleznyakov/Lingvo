package com.zheleznyakov.lingvo.language.en;

import static com.zheleznyakov.lingvo.language.en.EnSpellingHelper.makePossessive;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.basic.Noun;
import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.basic.util.WordFormatter;
import com.zheleznyakov.lingvo.basic.util.WordFormatter.FormName;

public class EnNoun extends EnWord implements Noun {

    private final String alternativeForm;

    private final Map<FormName, String> irregularForms;

    protected EnNoun(Builder builder) {
        super(builder.mainForm);
        alternativeForm = builder.alternativeForm;
        irregularForms = builder.irregularForms == null
                ? Word.EMPTY_IRREGULAR_FORMS
                : builder.irregularForms;
    }

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
        return WordFormatter.getForms(mainForm, irregularForms, EnNounFormName.values());
    }

    @Override
    @NotNull
    public String[] getFormsFull() {
        return alternativeForm == null
                ? getForms()
                : joinForms(getForms(), WordFormatter.getForms(alternativeForm, Word.EMPTY_IRREGULAR_FORMS, EnNounFormName.values()));
    }

    public static Builder builder(@NotNull String noun) {
        return new Builder(noun);
    }

    public static class Builder {
        private String mainForm;
        private String alternativeForm;
        private boolean properNoun = false;
        private Map<FormName, String> irregularForms;

        private Builder(String mainForm) {
            this.mainForm = mainForm;
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

        public EnNoun build() {
            return properNoun ? new EnProperNoun(this) : new EnNoun(this);
        }
    }

    public enum EnNounFormName implements WordFormatter.FormName {
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
    }

}
