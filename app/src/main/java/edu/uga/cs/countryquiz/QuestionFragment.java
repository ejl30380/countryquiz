package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {
    private QuizViewModel quizViewModel;
    private Question question;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable("question");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView questionText = view.findViewById(R.id.questionText);
        RadioGroup answersGroup = view.findViewById(R.id.answersGroup);

        questionText.setText("What continent is " + question.getCountry() + " located in?");
        int j = 1;
        // Dynamically add radio buttons for each answer
        for (String answer : question.getAnswers()) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(String.format("%d. %s",j,answer));
            answersGroup.addView(radioButton);
            j++;
        }

        answersGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = group.findViewById(checkedId);
            if (selectedRadioButton.getText().toString().substring(3).equals(question.getCorrectAnswer())) {
                quizViewModel.incrementScore();
            }
            for (int i = 0; i < group.getChildCount(); i++) {
                group.getChildAt(i).setEnabled(false);
            }
            if (isLastQuestion()) {
                navigateToResults();
            }
        });
        return view;
    }

    private boolean isLastQuestion() {
        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt("position", -1); // Assuming you pass the position
            int totalQuestions = quizViewModel.getQuiz().getQuestions().size();
            return position == totalQuestions - 1;
        }
        return false;
    }

    private void navigateToResults() {
        Bundle bundle = new Bundle();
        int score = quizViewModel.getScore(); // Assuming you have a method to get the score
        int totalQuestions = quizViewModel.getQuiz().getQuestions().size();

        bundle.putInt("correctAnswers", score);
        bundle.putInt("totalQuestions", totalQuestions);

        // Use NavController to navigate, passing the bundle
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_quizFragment_to_resultsFragment, bundle);
    }
}
