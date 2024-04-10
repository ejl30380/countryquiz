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

        // set the question textview
        questionText.setText("What continent is " + question.getCountry() + " located in?");
        int j = 1;
        // Add radio buttons for each answer
        for (String answer : question.getAnswers()) {
            RadioButton radioButton = new RadioButton(getContext());
            // set radio button text to be numbered with answer.
            radioButton.setText(String.format("%d. %s",j,answer));
            answersGroup.addView(radioButton);
            // increment counter
            j++;
        }

        //when an answer is clicked, check if the selected choice is correct and update score if so.
        answersGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = group.findViewById(checkedId);
            // need substring call here to get rid of the #. on the button text.
            if (selectedRadioButton.getText().toString().substring(3).equals(question.getCorrectAnswer())) {
                quizViewModel.incrementScore();
            }
            // set all buttons to false once user has selected answer.
            for (int i = 0; i < group.getChildCount(); i++) {
                group.getChildAt(i).setEnabled(false);
            }
            // navigate to results fragment once the last question has been answered.
            if (isLastQuestion()) {
                navigateToResults();
            }
        });
        return view;
    }

    // checks if the question is the last.
    private boolean isLastQuestion() {
        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt("position", -1);
            int totalQuestions = quizViewModel.getQuiz().getQuestions().size();
            return position == totalQuestions - 1;
        }
        return false;
    }

    private void navigateToResults() {
        // create bundle for the results fragment
        Bundle bundle = new Bundle();
        int score = quizViewModel.getScore();
        int totalQuestions = quizViewModel.getQuiz().getQuestions().size();

        bundle.putInt("correctAnswers", score);
        bundle.putInt("totalQuestions", totalQuestions);

        // Use NavController to navigate, passing the bundle
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_quizFragment_to_resultsFragment, bundle);
    }
}
