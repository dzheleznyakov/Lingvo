package com.zheleznyakov.lingvo.basic.implementations

import com.zheleznyakov.lingvo.basic.words.FormName

import java.util.function.Function

enum FakeFormName implements FormName {
    MAIN_FORM(true, 'a'),
    NOT_MANDATORY_FORM(false, 'b'),
    MANDATORY_AND_POSSIBLE_IRREGULAR(true, 'c'),
    NOT_MANDATORY_AND_IRREGULAR(false, 'd')

    final boolean isMandatory
    final String regularSuffix

    FakeFormName(boolean isMandatory, String regularSuffix) {
        this.isMandatory = isMandatory
        this.regularSuffix = regularSuffix
    }

    @Override
    boolean isMandatory() {
        return isMandatory
    }

    @Override
    Function<String, String> getStandardConverter() {
        return { it + regularSuffix }
    }

    @Override
    FormName getRoot() {
        return this
    }

}