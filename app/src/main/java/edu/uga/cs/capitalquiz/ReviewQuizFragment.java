package edu.uga.cs.capitalquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReviewQuizFragment extends Fragment {

    private static final String TAG = "ReviewQuizFragment";

    private QuizData quizData = null;
    private List<Quiz> quizzesList;

    private RecyclerView recyclerView;
    private QuizRecyclerAdapter recyclerAdapter;

    public ReviewQuizFragment() {
        // Required constructor
    }

    public static ReviewQuizFragment newInstance() {
        ReviewQuizFragment fragment = new ReviewQuizFragment();
        return fragment;
    } // newInstance

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        // Enable the search menu population.
        // When the parameter of this method is true, Android will call onCreateOptionsMenu on
        // this fragment, when the options menu is being built for the hosting activity.
        setHasOptionsMenu( true );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_review_quizzes, container, false );
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        recyclerView = getView().findViewById( R.id.recyclerView );


        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setLayoutManager( layoutManager );

        quizzesList = new ArrayList<Quiz>();

        // Create a QuizData instance, since we will need to save a new Quiz to the dn.
        // Note that even though more activities may create their own instances of the QuizData
        // class, we will be using a single instance of the QuizDBHelper object, since
        // that class is a singleton class.
        quizData = new QuizData( getActivity() );

        // Open that database for reading of the full list of quizzes.
        // Note that onResume() hasn't been called yet, so the db open in it
        // was not called yet!
        quizData.open();

        // Execute the retrieval of the quizzes in an asynchronous way,
        // without blocking the main UI thread.
        new QuizDBReader().execute();

    } // onViewCreated

    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of job leads, asynchronously.
    private class QuizDBReader extends AsyncTask<Void, List<Quiz>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved Quiz objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the quizzes review activity is started).
        @Override
        protected List<Quiz> doInBackground(Void... params) {
            List<Quiz> quizList = quizData.retrieveAllQuizzes();
            Collections.reverse(quizList);

            Log.d(TAG, "QuizDBReader: Quizzes retrieved: " + quizList.size());

            return quizList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute(List<Quiz> quizList) {
            Log.d(TAG, "QuizDBReader: quizList.size(): " + quizList.size());
            quizzesList.addAll(quizList);

            recyclerAdapter = new QuizRecyclerAdapter(getActivity(), quizzesList);
            recyclerView.setAdapter(recyclerAdapter);

        }

    } // QuizDBReader


    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of a quiz, asynchronously.
    public class QuizDBWriter extends AsyncTask<Quiz, Quiz> {

        // This method will run as a background process to write into db.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onClick listener of the Save button.
        @Override
        protected Quiz doInBackground( Quiz... quizzes ) {
            quizData.storeQuiz( quizzes[0] );
            return quizzes[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished.  Note that doInBackground returns a Quiz object.
        // That object will be passed as argument to onPostExecute.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( Quiz quiz ) {
            // Update the recycler view to include the new job lead
            quizzesList.add(quiz);

            // Sync the originalValues list in the recyler adapter to the new updated list (quizList)
       //     recyclerAdapter.sync();

            // Notify the adapter that an item has been inserted
         //   recyclerAdapter.notifyItemInserted(quizzesList.size() - 1 );

            // Reposition the view to show to newly added item by smoothly scrolling to it
            recyclerView.smoothScrollToPosition( quizzesList.size() - 1 );

            Log.d( TAG, "Quiz saved: " + quiz );
        }
    } // QuizDBWriter


    @Override
    public void onResume() {
        super.onResume();

        if (quizData != null && !quizData.isDBOpen()) {
            quizData.open();
            Log.d(TAG, "ReviewQuizFragment.onResume() : opening DB");
        }

        // Update the app name in the Action Bar to be the same as the app's name
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );

    }

    @Override
    public void onPause() {
        super.onPause();

        if (quizData != null) {
            quizData.close();
            Log.d(TAG, "ReviewQuizFragment.onPause : closing DB");
        }
    }



} // ReviewQuizFragment

