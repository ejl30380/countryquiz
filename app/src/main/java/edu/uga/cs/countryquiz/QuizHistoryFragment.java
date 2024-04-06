package edu.uga.cs.countryquiz;

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
        QuizData quizData = new QuizData(getContext());
        quizData.open();
        ArrayList<Quiz> quizzes = quizData.retrieveAllQuiz();
        quizData.close();

        QuizHistoryAdapter adapter = new QuizHistoryAdapter(quizzes);
        quizHistoryRecyclerView.setAdapter(adapter);
    }
}
