package edu.uga.cs.capitalquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class NewQuizSwipeFragment extends Fragment {

    private ViewPager2 mViewPager;

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

        mViewPager = view.findViewById(R.id.viewer);//Get ViewPager2 view
        mViewPager.setAdapter(new QuizPagerAdapter(getActivity()));//Attach the adapter with our ViewPagerAdapter passing the host activity

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabLayout, mViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(((QuizPagerAdapter)(mViewPager.getAdapter())).mFragmentNames[position]);//Sets tabs names as mentioned in ViewPagerAdapter fragmentNames array, this can be implemented in many different ways.
                    }
                }
        ).attach();

    }


}
