package edu.uga.cs.capitalquiz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all quizzes
 */
public class QuizRecyclerAdapter
        extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizHolder> {

    public static final String DEBUG_TAG = "QuizRecyclerAdapter";

    private final Context context;

    private List<Quiz> values;
    private List<Quiz> originalValues;

    public QuizRecyclerAdapter( Context context, List<Quiz> quizList ) {
        this.context = context;
        this.values = quizList;
        this.originalValues = new ArrayList<Quiz>( quizList );
    } // constructor

    // reset the originalValues to the current content of values
    public void sync() {
        originalValues = new ArrayList<Quiz>(values);
    }


    public static class QuizHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView result;
        TextView answered;
        TextView q1;
        TextView q2;
        TextView q3;
        TextView q4;
        TextView q5;
        TextView q6;

        public QuizHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            result = itemView.findViewById(R.id.result);
            answered = itemView.findViewById(R.id.answered);
            q1 = itemView.findViewById(R.id.q1);
            q2 = itemView.findViewById(R.id.q2);
            q3 = itemView.findViewById(R.id.q3);
            q4 = itemView.findViewById(R.id.q4);
            q5 = itemView.findViewById(R.id.q5);
            q6 = itemView.findViewById(R.id.q6);
        }

    } // QuizHolder

    @NonNull
    @Override
    // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
    // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
    // and false as the third parameter (don't attach to root).
    // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
    public QuizHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz, parent, false);
        return new QuizHolder(view);
    } // onCreateVewHolder

    // This method fills in the values of a holder to show a JobLead.
    // The position parameter indicates the position on the list of jobs list.
    @Override
    public void onBindViewHolder(QuizHolder holder, int position) {
        Quiz quiz = values.get(position);

        Log.d(DEBUG_TAG, "onBindViewHolder: " + quiz);

        holder.date.setText(quiz.getDate());
        holder.result.setText(String.valueOf(quiz.getResult()));
        holder.answered.setText(quiz.getNumAnswered());
        holder.q1.setText(quiz.getQ1());
        holder.q2.setText(quiz.getQ2());
        holder.q3.setText(quiz.getQ3());
        holder.q4.setText(quiz.getQ4());
        holder.q5.setText(quiz.getQ5());
        holder.q6.setText(quiz.getQ6());

    } // onBindViewHolder

    @Override
    public int getItemCount() {
        if (values != null)
            return values.size();
        else
            return 0;
    } // getItemCount



} // class
