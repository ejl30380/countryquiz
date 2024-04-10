package edu.uga.cs.countryquiz;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Quiz implements Serializable {
    public static final String TAG = "Quiz";
    private long id;
    private long date;
    private int result;
    private int numAnswered;
    private ArrayList<Question> questions = new ArrayList<Question>(6);

    public Quiz(){
        this.id=-1;
        this.date = 0;
        this.numAnswered = 0;
    }

    public Quiz(long date, int result){
        this.id = -1;
        this.date = (date);
        this.result = result;
        this.numAnswered = 0;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getLongDate(){
        return this.date;
    }

    public String getDate() {
        Log.i("Quiz", String.format("%d",this.date));
        return new Date(this.date).toString();
    }

    public int getResult() {
        return result;
    }


}
