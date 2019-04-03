package com.rr.project.myapplication.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rr.project.myapplication.R;
import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.databinding.RecyclerviewItemBinding;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {
    private final LayoutInflater mInflater;
    private final Context context;
    private List<Entry> listEntry;

    public EntryAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    class EntryViewHolder extends RecyclerView.ViewHolder {
        private RecyclerviewItemBinding binding;

        private EntryViewHolder(RecyclerviewItemBinding recyclerviewItemBinding) {
            super(recyclerviewItemBinding.getRoot());
            binding = recyclerviewItemBinding;
        }
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerviewItemBinding binding = DataBindingUtil.inflate(mInflater, R.layout.recyclerview_item_tab, parent, false);
        return new EntryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position) {
        /*if (listTabs != null) {
            Tab current = listTabs.get(position);
            holder.binding.settab(current);
        } else {
            // Covers the case of data not being ready yet.
//            holder.tabItemView.setText("No tab");
//            holder.binding.setText("No Tab"));
        }*/
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (listEntry != null)
            return listEntry.size();
        else return 0;
    }

    public void setEntry(List<Entry> listEntry) {
        this.listEntry = listEntry;
        notifyDataSetChanged();
    }

}