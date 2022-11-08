package edu.uga.cs.capitalquiz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizPagerAdapter extends FragmentStateAdapter {
    private final Fragment[] myFragments = new Fragment[] {//Initialize fragments views
//Fragment views are initialized like any other fragment (Extending Fragment)
            NewQuizFragment.newInstance(0),
            NewQuizFragment.newInstance(1),
            NewQuizFragment.newInstance(2),
            NewQuizFragment.newInstance(3),
            NewQuizFragment.newInstance(4),
            NewQuizFragment.newInstance(5),
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


    public QuizPagerAdapter(FragmentActivity fa){//Pager constructor receives Activity instance
        super(fa);
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
