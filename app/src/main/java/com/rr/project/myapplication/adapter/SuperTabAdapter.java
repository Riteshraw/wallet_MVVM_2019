package com.rr.project.myapplication.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rr.project.myapplication.BR;
import com.rr.project.myapplication.OnSuperTabClickListener;
import com.rr.project.myapplication.R;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.databinding.RecyclerviewItemBinding;
import com.rr.project.myapplication.viewModel.SuperTabViewModel;

import java.util.List;

public class SuperTabAdapter extends RecyclerView.Adapter<SuperTabAdapter.SuperTabViewHolder> {
    private final LayoutInflater mInflater;
    private final Context context;
    private final SuperTabViewModel viewModel;
    private List<SuperTab> superTabs;

    public SuperTabAdapter(Context context, SuperTabViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public SuperTabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerviewItemBinding binding = DataBindingUtil.inflate(mInflater, R.layout.recyclerview_item, parent, false);
        return new SuperTabViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SuperTabViewHolder holder, int position) {
        if (superTabs != null) {
            holder.bind(viewModel, position);
        } else {
            // Covers the case of data not being ready yet
            Toast.makeText(context, "No Super Tabs present in DB", Toast.LENGTH_SHORT).show();
        }
    }

    public void setSuperTabs(List<SuperTab> superTabs) {
        this.superTabs = superTabs;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // superTabs has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        return superTabs == null ? 0 : superTabs.size();
    }

    class SuperTabViewHolder extends RecyclerView.ViewHolder {
        final private RecyclerviewItemBinding binding;

        private SuperTabViewHolder(RecyclerviewItemBinding recyclerviewItemBinding) {
            super(recyclerviewItemBinding.getRoot());
            binding = recyclerviewItemBinding;
        }

        private void bind(SuperTabViewModel superTabViewModel, Integer position) {
            binding.setVariable(BR.superTab, superTabs.get(position));
            binding.setVariable(BR.position, position);
            binding.setVariable(BR.superTabVM, superTabViewModel);
            //OR like below, both produce same result
            //binding.setSuperTab(superTabs.get(position));
            //binding.setPosition(position);
            //binding.setSuperTabVM(superTabViewModel);
            binding.executePendingBindings();
        }
    }

}