package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

    private Question question;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //TODO
        questionText.setText(question.getCountry()); // Set the question text

        // Dynamically add radio buttons for each answer
        for (String answer : question.getAnswers()) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(answer);
            answersGroup.addView(radioButton);
        }

        // Add logic to handle answer selection if necessary

        return view;
    }
}
