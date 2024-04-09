package edu.uga.cs.countryquiz;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class QuizHistoryFragment extends Fragment {

    private RecyclerView quizHistoryRecyclerView;

    public QuizHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_history, container, false);

        quizHistoryRecyclerView = view.findViewById(R.id.quizHistoryRecyclerView);
        quizHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadQuizHistory();

        return view;
    }

    private void loadQuizHistory() {
        new RetrieveQuizTask(getContext()).execute();
    }

    public class RetrieveQuizTask extends AsyncTask<Void, ArrayList<Quiz>> {
        private Context context; // For database access

        public RetrieveQuizTask(Context context) {
            this.context = context;
        }

        @Override
        protected ArrayList<Quiz> doInBackground(Void... voids) {
            QuizData quizData = new QuizData(context);
            quizData.open();
            ArrayList<Quiz> quizzes = quizData.retrieveAllQuiz(); // Fetch quizzes
            quizData.close();
            return quizzes; // Return the fetched quizzes
        }

        @Override
        protected void onPostExecute(ArrayList<Quiz> quizzes) {
            // Update the RecyclerView with the fetched quizzes
            if (quizzes != null) {
                QuizHistoryAdapter adapter = new QuizHistoryAdapter(quizzes);
                quizHistoryRecyclerView.setAdapter(adapter);
            }
        }
    }
}
