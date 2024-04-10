package edu.uga.cs.countryquiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Question implements Serializable {
    public static final String TAG = "Question";
    public static final String[] CONTINENTS = {"Africa", "Asia", "Europe", "North America", "Oceania", "South America"};
    private String country;
    private String correctAnswer;
    private ArrayList<String> incorrectAnswers = new ArrayList<String>(2);
    private ArrayList<String> answers = new ArrayList<String>(3);

    public Question(String country, String correctAnswer, ArrayList<String> incorrectAnswers){
        this.country = country;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers.addAll(incorrectAnswers);
        this.answers.add(correctAnswer);
        this.answers.addAll(incorrectAnswers);
        Collections.shuffle(this.answers);
    }
    public String getCountry() {
        return country;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }


    public ArrayList<String> getAnswers() {
        return answers;
    }
}
