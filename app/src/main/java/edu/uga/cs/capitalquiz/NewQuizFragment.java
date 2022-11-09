package edu.uga.cs.capitalquiz;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
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

    private RadioGroup radioGroup;
    private RadioButton selectedAnswer;

    private String correctAnswer;
    private int addScore = 0;
    private boolean answered= false;

    private QuizData quizData;

    // which question to display in the fragment
    private int questNum;

    // list of question names to be passed in
    private ArrayList<String> questList;

    public final String[] questionTitles = new String[] {//Tabs names array
            "Question 1",
            "Question 2",
            "Question 3",
            "Question 4",
            "Question 5",
            "Question 6"
    };

    public final Integer[] questNumbers = new Integer[] {//Tabs names array
            1,
            2,
            3,
            4,
            5,
            6
    };


    public NewQuizFragment() {
        // Required empty public constructor
    }

    public static NewQuizFragment newInstance(int questNum, ArrayList<String> questList) {
        NewQuizFragment fragment = new NewQuizFragment();
        Bundle args = new Bundle();
        args.putInt( "questionNum", questNum );
        args.putStringArrayList("questList", questList);
        fragment.setArguments( args );
        return fragment;
    }


    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if( getArguments() != null ) {
            questNum = getArguments().getInt( "questionNum" );
        }
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

        List<Question> questions = quizData.getSpecificQuestions(questList);

        // set question TextView variable to appropriate question
        questionView.setText("What is the capital of " + questions.get(questNum).getName() + "?");

        // create ArrayList of answers, shuffle, then set to UI radioButtons
        ArrayList<String> answers = new ArrayList<>();
        correctAnswer = questions.get(questNum).getCapital();
        answers.add(questions.get(questNum).getCapital());
        answers.add(questions.get(questNum).getAdditional1());
        answers.add(questions.get(questNum).getAdditional2());
        Collections.shuffle(answers);

        answer1View.setText(answers.get(0));
        answer2View.setText(answers.get(1));
        answer3View.setText(answers.get(2));


    }


    @Override
    public void onResume() {
        super.onResume();

        radioGroup = getView().findViewById(R.id.radioGroup);
        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        selectedAnswer = getView().findViewById(selectedId);


        answer1View = getView().findViewById(R.id.answer1);
        answer2View = getView().findViewById(R.id.answer2);
        answer3View = getView().findViewById(R.id.answer3);


        // open the database in onResume
        if( quizData != null )
            quizData.open();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

    // We need to save job leads into a file as the activity stops being a foreground activity
    @Override
    public void onPause() {
        Log.d( TAG, "NewQuizFragment.onPause()" );
        super.onPause();

        Quiz thisQuiz = new Quiz();
    //    thisQuiz.setNumAnswered(questNumbers[questNum]);
      //  Log.d( TAG, "Number answered: " + questNumbers[questNum] );

        //if radioButton selected, save answer, record points *****
        radioGroup = getView().findViewById(R.id.radioGroup);
        answer1View = getView().findViewById(R.id.answer1);
        answer2View = getView().findViewById(R.id.answer2);
        answer3View = getView().findViewById(R.id.answer3);



        try {
            // get selected radio button from radioGroup
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            selectedAnswer = getView().findViewById(selectedId);
            Log.d( TAG, "Answer selected " + selectedAnswer.getText().toString() );
            answer1View.setClickable(false);
            answer2View.setClickable(false);
            answer3View.setClickable(false);

            if(correctAnswer.equals(selectedAnswer.getText().toString())){
                Log.d( TAG, "CORRECT ");
                addScore += 1;
                thisQuiz.setResult(addScore);
                Toast.makeText( getActivity(), "CORRECT ", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText( getActivity(), "INCORRECT \r\nCorrect Answer: " + correctAnswer, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText( getActivity(), "You skipped this question! ", Toast.LENGTH_SHORT).show();
            Log.d( TAG, "No answer selected ");
        }




        // close the database in onPause
        if( quizData != null )
            quizData.close();
    }





} // ReviewQuizFragment

