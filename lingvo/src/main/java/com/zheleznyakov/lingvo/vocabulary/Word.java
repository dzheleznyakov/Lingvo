package com.zheleznyakov.lingvo.vocabulary;

import com.zheleznyakov.lingvo.utils.Language;
import com.zheleznyakov.lingvo.utils.PartOfSpeach;

public interface Word {
    
    public Language getLanguage();
    
    public PartOfSpeach getPartOfSpeach();
    
    public String getBasicForm();
}