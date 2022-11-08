package edu.uga.cs.capitalquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FinishQuizFragment extends Fragment {

    private static final String TAG = "HelpFragment";

    private Button finishButton;
    private TextView dateText;
    private TextView scoreText;

    private QuizData quizData = null;
    private Quiz myQuiz;

    private ReviewQuizFragment reviewFragment;

    public FinishQuizFragment() {
        // Required empty public constructor
    }

    public static FinishQuizFragment newInstance() {
        FinishQuizFragment fragment = new FinishQuizFragment();
        return fragment;
    }


    private class SaveButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick( View v ) {

            getActivity().onBackPressed();

            quizData = new QuizData(getActivity());
            quizData.open();



        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_finish_quiz, container, false );
    }

    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view,savedInstanceState);
        finishButton = getView().findViewById(R.id.submitButton);
        dateText = getView().findViewById(R.id.dateTime);
        scoreText = getView().findViewById(R.id.finalScore);

        DateFormat date = new SimpleDateFormat("MMM dd yyyy, h:mm");
        String dateFormat = date.format(Calendar.getInstance().getTime());
        dateText.setText(dateFormat);


        finishButton.setOnClickListener( new SaveButtonClickListener()) ;

    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of a quiz, asynchronously.
    public class QuizDBWriter extends AsyncTask<Quiz, Quiz> {

        // This method will run as a background process to write into db.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onClick listener of the Save button.
        @Override
        protected Quiz doInBackground( Quiz... myQuiz ) {
            quizData.storeQuiz( myQuiz[0] );
            return myQuiz[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished.  Note that doInBackground returns a JobLead object.
        // That object will be passed as argument to onPostExecute.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( Quiz myQuiz ) {
            // Show a quick confirmation message
            Toast.makeText( getActivity(), "Quiz created on " + myQuiz.getDate(),
                    Toast.LENGTH_SHORT).show();

            Log.d( TAG, "Quiz saved: " + myQuiz );
        }


    }

}