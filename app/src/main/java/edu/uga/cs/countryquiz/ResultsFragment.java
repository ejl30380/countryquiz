package edu.uga.cs.countryquiz;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Calendar;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultsFragment extends Fragment {

    private TextView correctAnswersView;
    private TextView percentageView;
    private Button retakeQuizButton;
    private int correctAnswers;
    private int totalQuestions;

    public ResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make sure correct args were passed
        if (getArguments() != null) {
            correctAnswers = getArguments().getInt("correctAnswers");
            totalQuestions = getArguments().getInt("totalQuestions");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_results, container, false);
        // get views
        correctAnswersView = view.findViewById(R.id.correctAnswersTextView);
        percentageView = view.findViewById(R.id.percentageTextView);
        retakeQuizButton = view.findViewById(R.id.retakeQuizButton);

        // display the results
        displayResults();
        //lambda for taking another quiz
        retakeQuizButton.setOnClickListener(v -> retakeQuiz());

        return view;
    }

    private void displayResults() {
        // set views to display results to user
        correctAnswersView.setText(String.format("Correct Answers: %d", correctAnswers));
        double percentage = ((double) correctAnswers / totalQuestions) * 100;
        percentageView.setText(String.format("Percentage: %.2f%%", percentage));

        saveQuizResults(correctAnswers);
    }

    private void saveQuizResults(int correctAnswers) {
        // get data ready to be saved
        QuizData quizData = new QuizData(getContext());
        Date currentTime = Calendar.getInstance().getTime();
        Log.i("ResultsFragment",currentTime.toString());
        // async save quiz task call
        new SaveQuizTask(getContext()).execute(new Quiz(currentTime.getTime(), correctAnswers));
    }

    private void retakeQuiz() {
        // use nav controller to navigate back to start of quiz
        NavHostFragment.findNavController(ResultsFragment.this)
                .navigate(R.id.action_resultsFragment_to_quizFragment);
    }

    public class SaveQuizTask extends AsyncTask<Quiz, Void> {
        private Context context; // For database access

        public SaveQuizTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Quiz... quizzes) {
            if (quizzes.length > 0) {
                Quiz quiz = quizzes[0];
                QuizData quizData = new QuizData(context);
                quizData.open();
                quizData.storeQuiz(quiz);
                quizData.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }
}
