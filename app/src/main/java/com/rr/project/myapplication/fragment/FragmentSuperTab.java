package com.rr.project.myapplication.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rr.project.myapplication.BR;
import com.rr.project.myapplication.R;
import com.rr.project.myapplication.adapter.EntryAdapter;
import com.rr.project.myapplication.adapter.SuperTabAdapter;
import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.databinding.ActivityMainBinding;
import com.rr.project.myapplication.databinding.FragmentMainBinding;
import com.rr.project.myapplication.utils.Constants;
import com.rr.project.myapplication.viewModel.EntryViewModel;
import com.rr.project.myapplication.viewModel.SuperTabViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 22-Mar-18.
 */

public class FragmentSuperTab extends Fragment {

    private SuperTabViewModel sTabViewModel;
    private SuperTabAdapter sTabAdapter;
    private Context context;
    private RecyclerView recyclerView;

    public FragmentSuperTab() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();

        sTabViewModel = ViewModelProviders.of(this).get(SuperTabViewModel.class);
        FragmentMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        recyclerView = binding.recyclerView;
        sTabViewModel.getAllSuperTabs().observe(this, new Observer<List<SuperTab>>() {
            @Override
            public void onChanged(@Nullable List<SuperTab> entries) {
                sTabAdapter.setSuperTabs(entries);
                //sTabAdapter.notifyDataSetChanged();
            }
        });

        binding.setVariable(com.rr.project.myapplication.BR.superTabVM, sTabViewModel);

        sTabAdapter = new SuperTabAdapter(context, sTabViewModel);
        recyclerView.setAdapter(sTabAdapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//        ((GridLayoutManager) layoutManager).getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        //Check this link for gridlayout item spacing
        //https://www.dev2qa.com/android-recyclerview-example/
//        GridDividerDecoration gridItemDivider2 = new GridDividerDecoration(getApplicationContext());
//        recyclerView.addItemDecoration(gridItemDivider2);
        recyclerView.setLayoutManager(layoutManager);

        return binding.getRoot();
    }

    public void onStart() {
        super.onStart();
        /*entryViewModel = ViewModelProviders.of(getActivity()).get(EntryViewModel.class);

        entryViewModel.getListAllEntriesById(tab.getId()).observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                if (entries != null)
                    entryAdapter.setEntry(entries);
            }
        });*/

    }

}
