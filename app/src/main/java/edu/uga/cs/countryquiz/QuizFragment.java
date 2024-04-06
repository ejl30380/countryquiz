package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        viewPager = view.findViewById(R.id.quizViewPager);
        startQuizButton = view.findViewById(R.id.startQuizButton);

        startQuizButton.setOnClickListener(v -> startQuiz());
        viewPager.setVisibility(View.GONE); // Initially hide the ViewPager

        // Setup the ViewPager and its Adapter here after initializing the quiz

        return view;
    }

    private void startQuiz() {
        ArrayList<Country> countries = getAllCountries();
        ArrayList<Question> questions = generateQuestions(countries);

        quiz = new Quiz();
        quiz.setQuestions(questions);

        // Set up the ViewPager with the QuestionAdapter
        QuestionAdapter adapter = new QuestionAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setVisibility(View.VISIBLE);
        startQuizButton.setVisibility(View.GONE);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == quiz.getQuestions().size() - 1) {
                    // Last question is being shown
                    viewPager.setUserInputEnabled(false); // Optional: disable swiping to prevent user confusion
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_IDLE && viewPager.getCurrentItem() == quiz.getQuestions().size() - 1) {
                    // User has finished the quiz, navigate to the results
                    finishQuiz();
                }
            }
        });
    }

    private void finishQuiz() {
        int correctAnswers = quiz.getResult(); // Calculate or retrieve the number of correct answers
        int totalQuestions = 6; // Total number of questions in the quiz

        Bundle bundle = new Bundle();
        bundle.putInt("correctAnswers", correctAnswers);
        bundle.putInt("totalQuestions", totalQuestions);

        // Use NavController to navigate
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_quizFragment_to_resultsFragment, bundle);
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
        for (int i = 0; i < 6; i++) { // Assuming you have more than 6 countries
            Country country = countries.get(i);
            String correctAnswer = country.getContinent();

            // Generate incorrect answers
            ArrayList<String> incorrectAnswers = new ArrayList<>();
            for (String continent : Question.CONTINENTS) {
                if (!continent.equals(correctAnswer)) {
                    incorrectAnswers.add(continent);
                    if (incorrectAnswers.size() == 2) {
                        break;
                    }
                }
            }

            questions.add(new Question(country.getCountry(), correctAnswer, incorrectAnswers));
        }

        return questions;
    }
}
