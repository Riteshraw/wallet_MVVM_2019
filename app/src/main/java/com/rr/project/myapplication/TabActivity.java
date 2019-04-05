package com.rr.project.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rr.project.myapplication.adapter.ViewPagerAdapter;
import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.fragment.EditNameDialogFragment;
import com.rr.project.myapplication.fragment.FragmentTab;
import com.rr.project.myapplication.viewModel.SuperTabViewModel;
import com.rr.project.myapplication.viewModel.TabViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.img_toolbar_add_tab)
    ImageView img_toolbar_add_tab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    @BindView(R.id.txt_entry_date)
    TextView txt_entry_date;
    @BindView(R.id.edt_note)
    EditText edt_note;
    @BindView(R.id.edt_Amount)
    EditText edt_Amount;
    @BindView(R.id.btn_add_entry)
    Button btn_add_entry;
    @BindView(R.id.dialog_rb_debit)
    RadioButton dialog_rb_debit;
    @BindView(R.id.dialog_rb_credit)
    RadioButton dialog_rb_credit;

    BottomSheetBehavior sheetBehavior;

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
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        int superTabId = getIntent().getIntExtra(Constant.SUPER_TAB_ID, 0);
        String supTabName = getIntent().getStringExtra(Constant.SUPER_TAB_NAME);

        setSupportActionBar(toolbar);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabList, context);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabViewModel = ViewModelProviders.of(this).get(TabViewModel.class);

        tabViewModel.getListAllTabsById(superTabId).observe(this, new Observer<List<Tab>>() {
            @Override
            public void onChanged(@Nullable List<Tab> tabList1) {
                if (tabList1.size() > 0) {
                    tabList.clear();
                    tabList.addAll(tabList1);
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

        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        txt_entry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntry();
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

    private void addEntry() {
        ((FragmentTab) viewPagerAdapter.getCurrentFragment()).addEntry(tabList.get(viewPager.getCurrentItem()));
        /*entryViewModel.insertEntry(new Entry(
                "Initial debit", "40000", false, 1, new Date().getTime()
        ));*/
    }
}
