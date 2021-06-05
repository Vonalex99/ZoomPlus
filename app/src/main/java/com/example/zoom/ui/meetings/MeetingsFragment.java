package com.example.zoom.ui.meetings;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zoom.R;
import com.google.android.material.tabs.TabLayout;

public class MeetingsFragment extends Fragment {
    private View mView;
    private TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_meetings, container, false);


        tabLayout = (TabLayout) mView.findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Previous"));
        tabLayout.addTab(tabLayout.newTab().setText("Scheduled"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) mView.findViewById(R.id.viewPager);

        final MeetingsTabAdapter adapter = new MeetingsTabAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
     return mView;
    }
}