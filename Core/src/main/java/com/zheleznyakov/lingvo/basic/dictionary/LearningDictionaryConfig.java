package com.zheleznyakov.lingvo.basic.dictionary;

public class LearningDictionaryConfig {
    private Mode mode = Mode.FORWARD;
    private int maxLearnCount = 30;

    public static LearningDictionaryConfig getDefault() {
        return new LearningDictionaryConfig();
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public int getMaxLearnCount() {
        return maxLearnCount;
    }

    public void setMaxLearnCount(int maxLearnCount) {
        this.maxLearnCount = maxLearnCount;
    }

    public enum Mode {
        FORWARD,
        BACKWARD,
        TOGGLE
    }
}
