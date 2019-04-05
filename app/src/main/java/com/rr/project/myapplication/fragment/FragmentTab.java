package com.rr.project.myapplication.fragment;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rr.project.myapplication.R;
import com.rr.project.myapplication.TabActivity;
import com.rr.project.myapplication.WalletApplication;
import com.rr.project.myapplication.adapter.EntryAdapter;
import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.viewModel.EntryViewModel;
import com.rr.project.myapplication.viewModel.SuperTabViewModel;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 22-Mar-18.
 */

public class FragmentTab extends Fragment {

    @BindView(R.id.tab_recyclerView)
    RecyclerView tab_recyclerView;
    @BindView(R.id.fab_add_entry)
    FloatingActionButton fab_add_entry;

    private EntryViewModel entryViewModel;
    private EntryAdapter entryAdapter;
    private Context context;

    public FragmentTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        entryAdapter = new EntryAdapter(context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        tab_recyclerView.setLayoutManager(layoutManager);
        tab_recyclerView.setAdapter(entryAdapter);
        return view;
    }

    public void onStart() {
        super.onStart();
        entryViewModel = ViewModelProviders.of(getActivity()).get(EntryViewModel.class);

        entryViewModel.getListAllEntriesById(WalletApplication.getInstance().getTab().getId()).observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                if (entries != null)
                    entryAdapter.setEntry(entries);
            }
        });

        fab_add_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEntry(null);
            }
        });


    }

    public void addEntry(Tab tab) {
        entryViewModel.insertEntry(new Entry(
                "Initial debit", "500", false, tab.getId(), new Date().getTime()
        ));
    }

}
