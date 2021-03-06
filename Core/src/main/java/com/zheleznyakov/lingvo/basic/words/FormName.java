package com.zheleznyakov.lingvo.basic.words;

import java.util.function.Function;

public interface FormName {
    boolean isMandatory();
    Function<String, String> getStandardConverter();
    FormName getRoot();
}
