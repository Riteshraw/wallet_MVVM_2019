package com.rr.project.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rr.project.myapplication.adapter.ViewPagerAdapter;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.fragment.EditNameDialogFragment;
import com.rr.project.myapplication.viewModel.SuperTabViewModel;
import com.rr.project.myapplication.viewModel.TabViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabActivity extends AppCompatActivity {

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.img_toolbar_add_tab) ImageView img_toolbar_add_tab;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;

    private Context context;
    private TabViewModel tabViewModel;
    private ViewPagerAdapter viewPagerAdapter;

    private ArrayList<Tab> tabList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        context = this;
        ButterKnife.bind(this);

        int superTabId = getIntent().getIntExtra(Constant.SUPER_TAB_ID, 0);
        String supTabName = getIntent().getStringExtra(Constant.SUPER_TAB_NAME);

        setSupportActionBar(toolbar);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabViewModel = ViewModelProviders.of(this).get(TabViewModel.class);

        tabViewModel.getListAllTabsById(superTabId).observe(this, new Observer<List<Tab>>() {
            @Override
            public void onChanged(@Nullable List<Tab> tabList1) {
                if (tabList1.size() > 0) {
                    tabList.clear();
                    tabList.addAll((ArrayList<Tab>) tabList1);
                    Toast.makeText(context, "Tab add to list : " + tabList1.size(), Toast.LENGTH_SHORT).show();
                    viewPagerAdapter.notifyDataSetChanged();
                } else
                    addTab();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        img_toolbar_add_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTab();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setToolbarTitle((String) tab.getText());
//                WalletApplication.getInstance().setTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void addTab() {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance(Constant.TAB, false);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    private void setToolbarTitle(String tabName) {
        toolbar.setTitle(tabName);
    }
}
