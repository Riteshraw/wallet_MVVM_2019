package com.rr.project.myapplication.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.fragment.FragmentTab;
import com.rr.project.myapplication.utils.Constants;

import java.util.ArrayList;

/**
 * Created by admin on 18-Apr-18.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private ArrayList<Tab> listTab;
    private Fragment fragment;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<Tab> listTab, Context context) {
        super(fm);
        this.listTab = listTab;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        fragment = new FragmentTab();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.TAB, listTab.get(position));
        fragment.setArguments(bundle);
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

    public Fragment getCurrentFragment() {
        return fragment;
    }

}
