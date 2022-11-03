package edu.uga.cs.capitalquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * This fragment will contain the scroll view Quiz, occupied by state capital questions
 */
public class NewQuizFragment extends Fragment {

    private static final String TAG = "NewQuizFragment";

    private Quiz quiz = new Quiz();
    // populate Quiz with questions using QuizDBHelper

    public NewQuizFragment() {
        // Required empty public constructor
    }

    public static NewQuizFragment newInstance() {
        NewQuizFragment fragment = new NewQuizFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
    }



} // ReviewQuizFragment

