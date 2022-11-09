package edu.uga.cs.capitalquiz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        TextView info;

        public QuizHolder(View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.quizText);
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

        holder.info.setText(quiz.toString());

    } // onBindViewHolder


    @Override
    public int getItemCount() {
        if (values != null)
            return values.size();
        else
            return 0;
    } // getItemCount



} // class
