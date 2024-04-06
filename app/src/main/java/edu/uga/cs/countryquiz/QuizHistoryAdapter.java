package edu.uga.cs.countryquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.uga.cs.countryquiz.Quiz;

public class QuizHistoryAdapter extends RecyclerView.Adapter<QuizHistoryAdapter.ViewHolder> {

    private final List<Quiz> quizList;

    public QuizHistoryAdapter(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quiz quiz = quizList.get(position);
        holder.quizDateTextView.setText(quiz.getDate()); // Format this date as needed
        holder.quizResultTextView.setText(String.valueOf(quiz.getResult()) + "%");
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView quizDateTextView, quizResultTextView;

        ViewHolder(View view) {
            super(view);
            quizDateTextView = view.findViewById(R.id.quizDateTextView);
            quizResultTextView = view.findViewById(R.id.quizResultTextView);
        }
    }
}
