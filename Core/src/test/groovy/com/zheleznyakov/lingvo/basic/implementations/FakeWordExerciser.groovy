package com.zheleznyakov.lingvo.basic.implementations

import com.zheleznyakov.lingvo.basic.exercisers.Answer
import com.zheleznyakov.lingvo.basic.exercisers.Exercise
import com.zheleznyakov.lingvo.basic.dictionary.LearningDictionary
import com.zheleznyakov.lingvo.basic.dictionary.Record
import com.zheleznyakov.lingvo.basic.exercisers.WordExerciser
import com.zheleznyakov.lingvo.basic.words.GrammaticalWord

class FakeWordExerciser extends WordExerciser<FakeExercise, Answer> {
    FakeWordExerciser(LearningDictionary dictionary) {
        super(dictionary)
    }

    @Override
    protected void doSubmitAnswer(Answer answer) {
    }

    @Override
    protected FakeExercise getNextExercise(Record record) {
        return [record.word]
    }
}

class FakeExercise implements Exercise {
    final def word;
    FakeExercise(GrammaticalWord word) {
        this.word = word;
    }
}