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


public class FinishQuizFragment extends Fragment {

    private static final String TAG = "HelpFragment";

    private Button finishButton;

    public FinishQuizFragment() {
        // Required empty public constructor
    }

    public static FinishQuizFragment newInstance(int questNum) {
        FinishQuizFragment fragment = new FinishQuizFragment();
        Bundle args = new Bundle();
        args.putInt( "questionNum", questNum );
        fragment.setArguments( args );
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

        finishButton.setOnClickListener( new SaveButtonClickListener()) ;

    }

}