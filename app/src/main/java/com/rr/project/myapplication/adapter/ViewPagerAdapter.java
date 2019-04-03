package com.rr.project.myapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.fragment.FragmentTab;

import java.util.ArrayList;

/**
 * Created by admin on 18-Apr-18.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String currentMonth;
    private ArrayList<Tab> listTab;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<Tab> listTab) {
        super(fm);
        this.listTab = listTab;
//        this.currentMonth = currentMonth;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new FragmentTab();
        return fragment;
    }

    @Override
    public int getCount() {
        return listTab.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = listTab.get(position).getTabName();
        return title;
    }

    @Override
    public int getItemPosition(Object object) {
        // Causes adapter to reload all Fragments when
        // notifyDataSetChanged is called
        return POSITION_NONE;
    }

}
