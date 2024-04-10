package edu.uga.cs.countryquiz;

import androidx.lifecycle.ViewModel;

/**
 * Quiz ViewModel, keeps data across fragments and between lifecycle calls.
 */
public class QuizViewModel extends ViewModel {
    private int score = 0;
    private Quiz quiz;

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public void resetScore() {
        this.score = 0;
    }
}
