package com.rr.project.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rr.project.myapplication.adapter.ViewPagerAdapter;
import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.fragment.DatePickerFragment;
import com.rr.project.myapplication.fragment.EditNameDialogFragment;
import com.rr.project.myapplication.fragment.EntryDialogFragment;
import com.rr.project.myapplication.fragment.FragmentTab;
import com.rr.project.myapplication.utils.DecimalDigitsInputFilter;
import com.rr.project.myapplication.utils.Utils;
import com.rr.project.myapplication.viewModel.TabViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TabActivity extends AppCompatActivity {

    @BindView(R.id.img_toolbar_add_tab)
    ImageView img_toolbar_add_tab;
    @BindView(R.id.img_toolbar_add_entry)
    ImageView img_toolbar_add_entry;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.bottom_sheet)
    RelativeLayout layoutBottomSheet;
    @BindView(R.id.txt_entry_date)
    TextView txt_entry_date;
    @BindView(R.id.edt_note)
    EditText edt_note;
    @BindView(R.id.edt_Amount)
    EditText edt_Amount;
    @BindView(R.id.btn_add_entry)
    Button btn_add_entry;
    @BindView(R.id.rb_debit)
    RadioButton rb_debit;
    @BindView(R.id.rb_credit)
    RadioButton rb_credit;
    @BindView(R.id.dialog_header)
    TextView dialog_header;
    @BindView(R.id.dialog_rg)
    RadioGroup dialog_rg;

    BottomSheetBehavior sheetBehavior;

    private Context context;
    private TabViewModel tabViewModel;
    private ViewPagerAdapter viewPagerAdapter;

    private ArrayList<Tab> tabList = new ArrayList<>();
    private EntryDialogFragment entryDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        context = this;
        ButterKnife.bind(this);

        //limit edt_Amount filed to 2 decimal places
        edt_Amount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10, 2)});

        int superTabId = getIntent().getIntExtra(Constant.SUPER_TAB_ID, 0);
        String supTabName = getIntent().getStringExtra(Constant.SUPER_TAB_NAME);
        setToolbarTitle(supTabName);

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
//                    Toast.makeText(context, "Tab add to list : " + tabList1.size(), Toast.LENGTH_SHORT).show();
                    viewPagerAdapter.notifyDataSetChanged();
                } else
                    addTab();
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

        img_toolbar_add_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog(null);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                setToolbarTitle((String) tab.getText());
//                WalletApplication.getInstance().setTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        initialiseBottomSheet();

    }

    /**
     * manually opening / closing bottom sheet on button click
     */
    @OnClick(R.id.btn_add_entry)
    public void addEntryByBootomSheetClick() {
        if (validate())
            return;

        addEntry(
                edt_note.getText().toString(),
                Float.parseFloat(edt_Amount.getText().toString()),
                rb_debit.isChecked(),
                0,
                Utils.getEntryTime(txt_entry_date),
                true);

        bottomSheetToggle();
    }


    public void addTab() {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance(Constant.TAB, false);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    private void setToolbarTitle(String tabName) {
        toolbar.setTitle(tabName);
    }

    public void addEntry(String note, float amt, boolean isDebit, int tabId, long dateTime, boolean isLatestEntry) {
        ((FragmentTab) viewPagerAdapter.getCurrentFragment()).addEntry(
                new Entry(
                        note,
                        amt,
                        isDebit,
                        tabList.get(viewPager.getCurrentItem()).getId(),
                        dateTime,
                        txt_entry_date.getText().toString(),
                        true
                ), txt_entry_date.getText().toString().equals(Utils.getCurrentDateInString()));

        Utils.hideKeyboard(this);
    }

    public void deleteEntry(Entry entry) {
        ((FragmentTab) viewPagerAdapter.getCurrentFragment()).deleteEntry(entry);
    }

    public void editEntry(Entry entry) {
        showEditDialog(entry);

//        ((FragmentTab) viewPagerAdapter.getCurrentFragment()).deleteEntry(entry);
    }

    @OnClick(R.id.btn_cancel)
    public void onCancel(View view) {
        bottomSheetToggle();
        Utils.hideKeyboard(this);
    }

    private void initialiseBottomSheet() {
        setDate(Utils.getCurrentDateInString());

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
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
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //Stop scroll open/close
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void bottomSheetToggle() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            sheetBehavior.setPeekHeight(dialog_header.getHeight());

            resetBottomSheet();
        }

        Utils.hideKeyboard(this);
    }

    private void showEditDialog(Entry entry) {
        FragmentManager fm = getSupportFragmentManager();
        entryDialogFragment = EntryDialogFragment.newInstance("Add New Entry");
        Bundle bundle = new Bundle();
        bundle.putParcelable("ENTRY", entry);
        entryDialogFragment.setArguments(bundle);
        entryDialogFragment.show(fm, "fragment_new_entry");
    }

    @OnClick(R.id.dialog_header)
    void toggleBottomSheet() {
        bottomSheetToggle();
    }

    private void resetBottomSheet() {
        btn_add_entry.setText("Add Expense");
        edt_note.setText("");
//        txt_entry_date.setText("");
        edt_Amount.setText("");
    }

    private boolean validate() {
        if (Utils.getTextFromEditText(edt_note).equals("")) {
            Toast.makeText(context, "Please enter a note", Toast.LENGTH_SHORT).show();
            return true;
        } else if (Utils.getTextFromEditText(edt_Amount).equals("")) {
            Toast.makeText(context, "Please enter a amount", Toast.LENGTH_SHORT).show();
            return true;
        } else if (dialog_rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(context, "Please select a transaction type", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void setDate(String date) {
//        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        if (entryDialogFragment != null)
            entryDialogFragment.setDate(date);
        txt_entry_date.setText(date);
    }

    public void updateEntry(Entry editEntry, boolean isDateChange, long originalDate) {
        ((FragmentTab) viewPagerAdapter.getCurrentFragment()).updateEntry(editEntry, isDateChange, originalDate);
    }

    public void updateEntryNote(Entry editEntry) {
        ((FragmentTab) viewPagerAdapter.getCurrentFragment()).updateEntryNote(editEntry);
    }
}
