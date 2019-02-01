package com.example.vishnu.typroject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    String[] tabArray = new String[]{"Attendance","Contact"};
    Integer tabNumber = tabArray.length;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AttendanceFragment frag1 = new AttendanceFragment();
                return frag1;
//            case 1:
//                TimetableFragment frag2 = new TimetableFragment();
//                return frag2;
            case 1:
                UsersFragment frag3 = new UsersFragment();
                return frag3;
        }

            return null;
    }

    @Override
    public int getCount() {
        return tabNumber;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabArray[position];
    }
}
