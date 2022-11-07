package edu.uga.cs.capitalquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This fragment will contain the scroll view Quiz, occupied by state capital questions
 */
public class NewQuizFragment extends Fragment {

    private static final String TAG = "NewQuizFragment";
    private TextView questionView;
    private TextView questionTitle;
    private RadioButton answer1View;
    private RadioButton answer2View;
    private RadioButton answer3View;

    private Quiz newQuiz = new Quiz();
    // populate Quiz with questions using QuizDBHelper

    private QuizData quizData;
    private String a1;
    private String a2;
    private String a3;

    // which question to display in the fragment
    private int questNum;

    public final String[] questionTitles = new String[] {//Tabs names array
            "Question 1",
            "Question 2",
            "Question 3",
            "Question 4",
            "Question 5",
            "Question 6"
    };


    public NewQuizFragment() {
        // Required empty public constructor
    }

    public static NewQuizFragment newInstance(int questNum) {
        NewQuizFragment fragment = new NewQuizFragment();
        Bundle args = new Bundle();
        args.putInt( "questionNum", questNum );
        fragment.setArguments( args );
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

        // save UI elements into variables
        questionView = getView().findViewById(R.id.question);
        questionTitle = getView().findViewById(R.id.titleQuestion);
        answer1View = getView().findViewById(R.id.answer1);
        answer2View = getView().findViewById(R.id.answer2);
        answer3View = getView().findViewById(R.id.answer3);

        questionTitle.setText(questionTitles[questNum]);

        quizData = new QuizData(getActivity());
        quizData.open();
        List<Question> questList = quizData.generateQuestions();
        Collections.shuffle(questList);
        Log.d( TAG, "How long is list: " + questList.size() );


        // get questions from generated list
        Question q1 = questList.get(0);
        Question q2 = questList.get(1);
        Question q3 = questList.get(2);
        Question q4 = questList.get(3);
        Question q5 = questList.get(4);
        Question q6 = questList.get(5);

        newQuiz.setQ1(q1.toString());
        newQuiz.setQ2(q2.toString());
        newQuiz.setQ3(q3.toString());
        newQuiz.setQ4(q4.toString());
        newQuiz.setQ5(q5.toString());
        newQuiz.setQ6(q6.toString());

        // set question TextView variable to appropriate question
        questionView.setText(questList.get(questNum).toString());

        // create ArrayList of answers, shuffle, then set to UI radioButtons
        ArrayList<String> answers = new ArrayList<>();
        answers.add(questList.get(questNum).getCapital());
        answers.add(questList.get(questNum).getAdditional1());
        answers.add(questList.get(questNum).getAdditional2());
        Collections.shuffle(answers);

        answer1View.setText(answers.get(0));
        answer2View.setText(answers.get(1));
        answer3View.setText(answers.get(2));

        quizData.storeQuiz(newQuiz);
    }


    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of a job lead, asynchronously.
    public class QuizDataDBWriter extends AsyncTask<Quiz, Quiz> {

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


            Log.d( TAG, "Job lead saved: " + myQuiz );
        }
    }









} // ReviewQuizFragment

