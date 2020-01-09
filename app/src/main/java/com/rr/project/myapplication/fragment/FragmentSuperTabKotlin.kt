package com.rr.project.myapplication.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rr.project.myapplication.BR
import com.rr.project.myapplication.R
import com.rr.project.myapplication.adapter.SuperTabAdapter
import com.rr.project.myapplication.databinding.FragmentMainBinding
import com.rr.project.myapplication.viewModel.SuperTabViewModel

class FragmentSuperTabKotlin : Fragment() {
    private var sTabViewModel: SuperTabViewModel? = null
    private var sTabAdapter: SuperTabAdapter? = null
    private var recyclerView: RecyclerView? = null

    fun FragmentSuperTab() {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sTabViewModel = ViewModelProviders.of(this).get(SuperTabViewModel::class.java)
        val binding:FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        recyclerView = binding.recyclerView
        sTabViewModel!!.allSuperTabs.observe(this, Observer { entries ->
            sTabAdapter!!.setSuperTabs(entries)
            //sTabAdapter.notifyDataSetChanged();
        })
        binding.setVariable(BR.superTabVM, sTabViewModel)
        sTabAdapter = SuperTabAdapter(context, sTabViewModel)
        recyclerView?.setAdapter(sTabAdapter)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(getContext(), 2) as RecyclerView.LayoutManager
        recyclerView?.setLayoutManager(layoutManager)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
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