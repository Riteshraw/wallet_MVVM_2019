package com.rr.project.myapplication.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rr.project.myapplication.R;
import com.rr.project.myapplication.adapter.EntryAdapter;
import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.utils.Constants;
import com.rr.project.myapplication.viewModel.EntryViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 22-Mar-18.
 */

public class FragmentTab extends Fragment {

    private Tab tab;
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

        tab = (Tab) getArguments().getSerializable(Constants.TAB);

        entryAdapter = new EntryAdapter(context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        tab_recyclerView.setLayoutManager(layoutManager);
        tab_recyclerView.setAdapter(entryAdapter);

        tab_recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

    public void onStart() {
        super.onStart();
        entryViewModel = ViewModelProviders.of(getActivity()).get(EntryViewModel.class);

        entryViewModel.getListAllEntriesById(tab.getId()).observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                if (entries != null)
                    entryAdapter.setEntry(entries);
            }
        });

        fab_add_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addEntry(null);
            }
        });

    }

    public void addEntry(Entry entry, boolean isCurrentDateEntry) {
        entryViewModel.insertEntry(entry, isCurrentDateEntry);
    }

    public void deleteEntry(Entry entry) {
        entryViewModel.deleteEntry(entry);
    }

    public void updateEntry(Entry editEntry, boolean isDateChange, long originalDate) {
        entryViewModel.updateEntry(editEntry,isDateChange, originalDate);
    }

    public void updateEntryNote(Entry editEntry) {
        entryViewModel.updateEntryNote(editEntry);
    }
}
