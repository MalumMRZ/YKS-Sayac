package com.yusufmirza.theyksproject.followsubject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerAdapterForLessons extends FragmentStateAdapter {

    ArrayList<Fragment> fragmentArrayList;

    public ViewPagerAdapterForLessons(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragmentArrayList) {
        super(fragmentActivity);
        this.fragmentArrayList = fragmentArrayList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = fragmentArrayList.get(position);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }
}
