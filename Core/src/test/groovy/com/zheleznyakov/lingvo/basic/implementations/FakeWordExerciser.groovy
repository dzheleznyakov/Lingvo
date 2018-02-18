package com.zheleznyakov.lingvo.basic.implementations

import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.Record
import com.zheleznyakov.lingvo.basic.dictionary.WordExerciser
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord

class FakeWordExerciser extends WordExerciser<GrammaticalWord, String> {
    FakeWordExerciser(LearningDictionary dictionary) {
        super(dictionary)
    }

    @Override
    protected void doSubmitAnswer(String answer) {
    }

    @Override
    protected GrammaticalWord getNextExercise(Record record) {
        return record.word
    }
}
