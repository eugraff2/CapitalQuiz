package edu.uga.cs.capitalquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

            //    Quiz myquiz = new Quiz();

            // Store this new quiz in the database asynchronously,
            // without blocking the UI thread.
            //  new QuizDBWriter().execute( myquiz );
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

}