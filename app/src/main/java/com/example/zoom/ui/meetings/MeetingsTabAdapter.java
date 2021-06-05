
package com.example.zoom.ui.meetings;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class MeetingsTabAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MeetingsTabAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PreviousMeetingsFragment previousFragment = new PreviousMeetingsFragment();
                return previousFragment;
            case 1:
                ScheduledMeetingsFragment scheduledFragment = new ScheduledMeetingsFragment();
                return scheduledFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}