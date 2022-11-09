package edu.uga.cs.capitalquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuizPagerAdapter extends FragmentStateAdapter {

    private ArrayList<String> questList;

    private final Fragment[] myFragments = new Fragment[7];

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
        this.questList = questListIn;
        for (int i = 0; i < 6; i++) {
            NewQuizFragment newFrag = NewQuizFragment.newInstance(i, questListIn);
            myFragments[i] = newFrag;
        } // for i
        myFragments[6] = FinishQuizFragment.newInstance();
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
