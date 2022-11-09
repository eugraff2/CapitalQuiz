package edu.uga.cs.capitalquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuizPagerAdapter extends FragmentStateAdapter {

    private ArrayList<String> questList;
    private final Fragment[] myFragments = new Fragment[] { //Initialize fragments views
//Fragment views are initialized like any other fragment (Extending Fragment)
            NewQuizFragment.newInstance(0, questList),
            NewQuizFragment.newInstance(1, questList),
            NewQuizFragment.newInstance(2, questList),
            NewQuizFragment.newInstance(3, questList),
            NewQuizFragment.newInstance(4, questList),
            NewQuizFragment.newInstance(5, questList),
            FinishQuizFragment.newInstance(),
    };

    public final String[] mFragmentNames = new String[] {//Tabs names array
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            " "
    };


    public QuizPagerAdapter(FragmentActivity fa, ArrayList<String> questListIn){//Pager constructor receives Activity instance
        super(fa);
        questList = questListIn;
    }

    @Override
    public int getItemCount() {
        return myFragments.length;//Number of fragments displayed
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return myFragments[position];
    }



}
