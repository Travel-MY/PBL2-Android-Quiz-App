package com.example.countryquizz;

public class Quiz {
    private int questions;
    private boolean answerTrue;
    private int imageName;

    public Quiz(int questions, int imageName, boolean answerTrue) {
        this.questions = questions;
        this.answerTrue = answerTrue;
        this.imageName = imageName;
    }

    public int getQuestions() {
        return questions;
    };
    public int getImageName() {return  imageName;};
    public  boolean isAnswerTrue() {
        return  answerTrue;
    };

}
