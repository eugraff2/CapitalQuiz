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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

        quizData = new QuizData(getActivity());
        quizData.open();

        myQuiz = new Quiz();

        List<Question> questList = quizData.generateQuestions();
        Collections.shuffle(questList);
        ArrayList<String> questions = new ArrayList<>();
        ArrayList<Integer> fragmentScores = new ArrayList<>();

        // get questions from generated list and their names

        Question q1 = questList.get(0);
        questions.add(q1.getName());
        Question q2 = questList.get(1);
        questions.add(q2.getName());
        Question q3 = questList.get(2);
        questions.add(q3.getName());
        Question q4 = questList.get(3);
        questions.add(q4.getName());
        Question q5 = questList.get(4);
        questions.add(q5.getName());
        Question q6 = questList.get(5);
        questions.add(q6.getName());

        myQuiz.setQ1(q1.toString());
        myQuiz.setQ2(q2.toString());
        myQuiz.setQ3(q3.toString());
        myQuiz.setQ4(q4.toString());
        myQuiz.setQ5(q5.toString());
        myQuiz.setQ6(q6.toString());

        mViewPager = view.findViewById(R.id.viewer);//Get ViewPager2 view
        mViewPager.setAdapter(new QuizPagerAdapter(getActivity(), questions, fragmentScores, myQuiz));//Attach the adapter with our ViewPagerAdapter passing the host activity

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, mViewPager,
                (tab, position) -> {
                    tab.setText(((QuizPagerAdapter)(mViewPager.getAdapter())).mFragmentNames[position]);//Sets tabs names as mentioned in ViewPagerAdapter fragmentNames array, this can be implemented in many different ways.
                }
        ).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getText().equals(" ")){
                    DateFormat date = new SimpleDateFormat("MMM dd yyyy, h:mm");
                    String dateFormat = date.format(Calendar.getInstance().getTime());
                    myQuiz.setDate(dateFormat);
                    Log.d( TAG, "Quiz date saved: " + myQuiz.getDate() );




                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                if(tab.getText().equals("6")) {
                    for (int i = 0; i < fragmentScores.size(); i++) {
                        Log.d(TAG, "NEWEST Fragment Score List, index: " + i + " , score:" + fragmentScores.get(i));
                    }
                }

                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);

            }


            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
