package com.zheleznyakov.lingvo.en;

import java.util.Set;
import java.util.UUID;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.zheleznyakov.lingvo.basic.Language;
import com.zheleznyakov.lingvo.basic.Word;

public abstract class EnWord implements Word {
    public static final Set<String> VOWELS_STRICT = ImmutableSet.of("a", "o", "e", "u", "i");
    public static final Set<String> VOWELS_FULL = ImmutableSet.<String>builder()
            .addAll(VOWELS_STRICT).add("y").build();
    public static final Set<String> END_CONSONANT = ImmutableSet
            .of("b", "d", "g", "l", "m", "n", "p", "r", "s", "t", "z");
    public static final Set<String> SIBILANTS = ImmutableSet.of("x", "ch", "sh", "s");
    public static final Set<String> APPROXIMANTS = ImmutableSet.of("r", "l");

    protected final String mainForm;
    protected String transcription;
    private final UUID uuid = UUID.randomUUID();

    protected EnWord(Builder builder) {
        verifyChars(builder.mainForm);
        mainForm = builder.mainForm;
        transcription = builder.transcription;
    }

    protected void verifyChars(String form) {
        if (!form.matches("[a-zA-Z'\\-]*")) {
            throw new IllegalArgumentException("Word " + form + " contains illegal symbols for English");
        }
    }

    protected EnWord(String mainForm) {
        this.mainForm = mainForm;
    }

    @Override
    public String getMainForm() {
        return mainForm;
    }

    @Override
    public Language getLanguage() {
        return null;//Language.ENGLISH;
    }

    protected static String[] joinForms(String[] mainDeclensions, String[] alternativeDeclensions) {
        String[] declensionsFull = new String[mainDeclensions.length];
        for (int i = 0; i < mainDeclensions.length; i++) {
            declensionsFull[i] = mainDeclensions[i] + "/" + alternativeDeclensions[i];
        }
        return declensionsFull;
    }

    public String getTranscription() {
        return "[" + transcription + "]";
    }

    public static abstract class Builder<T extends EnWord> {
        private String mainForm;
        private String transcription;

        protected Builder(String mainForm) {
            this.mainForm = mainForm;
        }

        public Builder transcription(String transcription) {
            this.transcription = transcription;
            return this;
        }

        public abstract T build();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mainForm", mainForm)
                .add("id", uuid)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnWord word = (EnWord) o;
        return Objects.equal(uuid, word.uuid);
    }
}
