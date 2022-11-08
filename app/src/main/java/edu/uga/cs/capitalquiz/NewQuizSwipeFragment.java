package edu.uga.cs.capitalquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class NewQuizSwipeFragment extends Fragment {

    private static final String TAG = "NewQuizSwipeFragment";

    private ViewPager2 mViewPager;

    private static Quiz myQuiz;
    private List<Quiz> collectionQuiz;

    private QuizData quizData;

    public NewQuizSwipeFragment() {
        // Required empty public constructor
    }

    public static NewQuizSwipeFragment newInstance() {
        NewQuizSwipeFragment fragment = new NewQuizSwipeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_quiz_swipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        myQuiz = new Quiz();

        quizData = new QuizData(getActivity());
        quizData.open();
        List<Question> questList = quizData.generateQuestions();
        Collections.shuffle(questList);
        Log.d( TAG, "How long is list: " + questList.size() );


        mViewPager = view.findViewById(R.id.viewer);//Get ViewPager2 view
        mViewPager.setAdapter(new QuizPagerAdapter(getActivity()));//Attach the adapter with our ViewPagerAdapter passing the host activity

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, mViewPager,
                (tab, position) -> {
                    tab.setText(((QuizPagerAdapter)(mViewPager.getAdapter())).mFragmentNames[position]);//Sets tabs names as mentioned in ViewPagerAdapter fragmentNames array, this can be implemented in many different ways.
                }
        ).attach();

        new QuizDBReader().execute();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals(" ")){
                    quizData.open();
                    quizData.storeQuiz(myQuiz);

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of quizzes, asynchronously.
    private class QuizDBReader extends AsyncTask<Void, List<Quiz>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved Quiz objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the job leads review activity is started).
        @Override
        protected List<Quiz> doInBackground( Void... params ) {
            List<Quiz> collectionQuiz = quizData.retrieveAllQuizzes();

            Log.d( TAG, "QuizDBReader: Quizzes retrieved: " + collectionQuiz.size() );

            return collectionQuiz;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( List<Quiz> quizList ) {
            Log.d( TAG, "QuizDBReader: quizList.size(): " + quizList.size() );
//            collectionQuiz.addAll(quizList);

        }
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
