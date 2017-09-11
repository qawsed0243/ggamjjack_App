package com.ggam.jjac.voca.Vocacard;

/**
 * Created by YoungJung on 2017-06-19.
 */

public class Voca {
    int index;
    String word;
    String mean1;
    String mean2;
    String mean3;
    String grammar;

    public Voca(int index, String word, String mean1, String mean2, String mean3, String grammar)
    {
        this.word = word;
        this.mean1 = mean1;
        this.index = index;
        this.mean2 = mean2;
        this.mean3 = mean3;
        this.grammar = grammar;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean1() {
        return mean1;
    }

    public void setMean1(String mean1) {
        this.mean1 = mean1;
    }

    public String getMean2() {
        return mean2;
    }

    public void setMean2(String mean2) {
        this.mean2 = mean2;
    }

    public String getMean3() {
        return mean3;
    }

    public void setMean3(String mean3) {
        this.mean3 = mean3;
    }

    public String getGrammar() {
        return grammar;
    }

    public void setGrammar(String grammar) {
        this.grammar = grammar;
    }
}
