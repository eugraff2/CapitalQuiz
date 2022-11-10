package edu.uga.cs.capitalquiz;

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
    private int scoreForFragment = -1;
    private int questionsAnswered = 0;

    private int finalScore = 0;

    private static Quiz finishQuiz;
    private QuizData quizData;
    // list of question names to be passed in
    private ArrayList<String> questList;
    // list of fragment scores to be passed in
    private ArrayList<Integer> fragmentScores;

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


    public static NewQuizFragment newInstance(int questNum, ArrayList<String> questList, ArrayList<Integer> fragmentScores, Quiz finalQuiz) {
        NewQuizFragment fragment = new NewQuizFragment();
        Bundle args = new Bundle();
        finishQuiz = finalQuiz;
        Log.d(TAG, "NEW Quiz Fragment Q1: " +  finishQuiz.toString());
        args.putInt( "questionNum", questNum );
        args.putSerializable("quiz", finishQuiz);
        args.putStringArrayList("questList", questList);
        args.putIntegerArrayList("scoreList", fragmentScores);
        fragment.setArguments( args );
        return fragment;
    }


    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if( getArguments() != null ) {
            questNum = getArguments().getInt( "questionNum" );
            questList = getArguments().getStringArrayList("questList");
            fragmentScores = getArguments().getIntegerArrayList("scoreList");
            finishQuiz = (Quiz) getArguments().getSerializable("quiz");

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
        radioGroup = getView().findViewById(R.id.radioGroup);



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

    // We need to save quiz into a file as the activity stops being a foreground activity
    @Override
    public void onPause() {
        Log.d( TAG, "NewQuizFragment.onPause()" );
        super.onPause();

        //if radioButton selected, save answer, record points *****
        radioGroup = getView().findViewById(R.id.radioGroup);
        answer1View = getView().findViewById(R.id.answer1);
        answer2View = getView().findViewById(R.id.answer2);
        answer3View = getView().findViewById(R.id.answer3);

        // method used to check score for each fragment
        checkScoreForFragment();

        // close the database in onPause
        if( quizData != null )
            quizData.close();
    }


    public void checkScoreForFragment(){

        try {
            // get selected radio button from radioGroup
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            selectedAnswer = getView().findViewById(selectedId);
            Log.d( TAG, "Answer selected " + selectedAnswer.getText().toString() );
            questionsAnswered = 1;
            // freeze the buttons after question is answered
            answer1View.setClickable(false);
            answer2View.setClickable(false);
            answer3View.setClickable(false);

            if(correctAnswer.equals(selectedAnswer.getText().toString())){
                Log.d( TAG, "CORRECT ");
                scoreForFragment = 1;
                //  Log.d( TAG, "NewQuizFragment Result: " + thisQuiz.getResult() );
                Toast.makeText( getActivity(), "CORRECT ", Toast.LENGTH_SHORT).show();

            } else {
                scoreForFragment = 0;
                Toast.makeText( getActivity(), "INCORRECT \r\nCorrect Answer: " + correctAnswer, Toast.LENGTH_SHORT).show();
            }
            finishQuiz.setNumAnswered(finishQuiz.getNumAnswered() + questionsAnswered);
            Log.d( TAG, "Question Answered: " + questionsAnswered + " getNumAnswered: " + finishQuiz.getNumAnswered());
            finishQuiz.setResult(finishQuiz.getResult() + scoreForFragment);



        } catch (Exception e) {
            scoreForFragment = 0;
            e.printStackTrace();
            Toast.makeText( getActivity(), "You skipped this question! ", Toast.LENGTH_SHORT).show();
            Log.d( TAG, "No answer selected ");
        }


        fragmentScores.add(scoreForFragment);
        for (int i = 0; i < fragmentScores.size(); i++) {
            Log.d(TAG, " Fragment Score List, index: " + i + " , score:" + fragmentScores.get(i));
            finalScore += fragmentScores.get(i);
        }
        Log.d(TAG, "Fragment Final Score: " +  finalScore);

       finishQuiz.setResult(finalScore);


    }


} // ReviewQuizFragment

