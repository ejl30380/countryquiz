package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {
    public static final String TAG = "QuizFragment";
    private ViewPager2 viewPager;
    private Button startQuizButton;
    private Quiz quiz;
    private QuizViewModel quizViewModel;
    private TextView textView;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

        viewPager = view.findViewById(R.id.quizViewPager);
        startQuizButton = view.findViewById(R.id.startQuizButton);
        textView = view.findViewById(R.id.textView);

        startQuizButton.setOnClickListener(v -> startQuiz());
        viewPager.setVisibility(View.GONE);

        return view;
    }

    private void startQuiz() {
        ArrayList<Country> countries = getAllCountries();
        ArrayList<Question> questions = generateQuestions(countries);

        quiz = new Quiz();
        quiz.setQuestions(questions);
        quizViewModel.setQuiz(quiz);
        quizViewModel.resetScore();

        QuestionAdapter adapter = new QuestionAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setVisibility(View.VISIBLE);
        startQuizButton.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
    }

    private class QuestionAdapter extends FragmentStateAdapter {
        public QuestionAdapter(Fragment fragment) {
            super(fragment);
        }

        @Override
        public Fragment createFragment(int position) {
            QuestionFragment questionFragment = new QuestionFragment();
            Bundle args = new Bundle();
            args.putSerializable("question", quiz.getQuestions().get(position));
            args.putInt("position",position);
            questionFragment.setArguments(args);
            return questionFragment;
        }

        @Override
        public int getItemCount() {
            return quiz.getQuestions().size();
        }
    }


    private ArrayList<Country> getAllCountries() {
        CountryData countryData = new CountryData(getContext());
        countryData.open();
        ArrayList<Country> countries = countryData.retrieveAllCountries();
        countryData.close();
        return countries;
    }

    private ArrayList<Question> generateQuestions(List<Country> countries) {
        ArrayList<Question> questions = new ArrayList<>();
        Collections.shuffle(countries); // Shuffle the countries list to randomize
        for (int i = 0; i < 6; i++) {
            Country country = countries.get(i);
            String correctAnswer = country.getContinent();

            ArrayList<String> possibleAnswers = new ArrayList<>(Arrays.asList(Question.CONTINENTS));
            Collections.shuffle(possibleAnswers); // Shuffle possible answers to randomize

            ArrayList<String> incorrectAnswers = new ArrayList<>();
            for (String answer : possibleAnswers) {
                if (!answer.equals(correctAnswer) && incorrectAnswers.size() < 2) {
                    incorrectAnswers.add(answer); // Add incorrect answer until we have 2
                }
            }

            questions.add(new Question(country.getCountry(), correctAnswer, incorrectAnswers));
        }

        return questions;
    }
}
