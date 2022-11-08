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

    public static NewQuizSwipeFragment newInstance(Quiz currentQuiz) {
        NewQuizSwipeFragment fragment = new NewQuizSwipeFragment();
        myQuiz = currentQuiz;
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

        mViewPager = view.findViewById(R.id.viewer);//Get ViewPager2 view
        mViewPager.setAdapter(new QuizPagerAdapter(getActivity()));//Attach the adapter with our ViewPagerAdapter passing the host activity

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
                    quizData.open();
                    DateFormat date = new SimpleDateFormat("MMM dd yyyy, h:mm");
                    String dateFormat = date.format(Calendar.getInstance().getTime());
                    myQuiz.setDate(dateFormat);
                    // throwing Null Object Reference error when FinishQuizFragment is added
                    quizData.storeQuiz(myQuiz);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);
                myQuiz.setNumAnswered(Integer.parseInt(tab.getText().toString()));
                Log.d( TAG, "NewQuizSwipeFragment NumAnswered: " +  Integer.parseInt(tab.getText().toString()));
            }


            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
