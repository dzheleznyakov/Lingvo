package com.zheleznyakov.lingvo.language.en;

import static com.zheleznyakov.lingvo.language.en.EnSpellingHelper.endsInS;

import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import com.zheleznyakov.lingvo.basic.Word;
import com.zheleznyakov.lingvo.basic.util.WordFormatter;

public class EnProperNoun extends EnNoun {

    EnProperNoun(Builder builder) {
        super(builder);
    }

    @Override
    public boolean isProperNoun() {
        return true;
    }

    @Override
    @NotNull
    public String[] getForms() {
        return WordFormatter.getForms(mainForm, Word.EMPTY_IRREGULAR_FORMS, EnProperNounFormName.values());
    }

    @Override
    @NotNull
    public String[] getFormsFull() {
        if (endsInS(mainForm)) {
            String[] declensions = getForms();
            declensions[1] += "/" + EnSpellingHelper.makePossessive(mainForm);
            return declensions;
        } else {
            return getForms();
        }
    }

    public enum EnProperNounFormName implements WordFormatter.FormName {
        NOMINATIVE_SINGLE(Function.identity()),
        POSSESSIVE_SINGLE(form -> form + "'s");

        private final Function<String, String> standardConverter;

        EnProperNounFormName(Function<String, String> standardConverter) {
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
