package com.rr.project.myapplication.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rr.project.myapplication.R;
import com.rr.project.myapplication.TabActivity;
import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.databinding.RecyclerviewItemTabBinding;

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
        private RecyclerviewItemTabBinding binding;

        private EntryViewHolder(RecyclerviewItemTabBinding recyclerviewItemTabBinding) {
            super(recyclerviewItemTabBinding.getRoot());
            binding = recyclerviewItemTabBinding;
        }
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerviewItemTabBinding binding = DataBindingUtil.inflate(mInflater, R.layout.recyclerview_item_tab, parent, false);
        return new EntryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, final int position) {
        if (listEntry != null) {
            //(double)Math.round(101.850006*100)/100
            Entry current = listEntry.get(position);
            holder.binding.setEntry(current);
            if (position == 0)
                holder.binding.txtMonthHeader.setVisibility(View.VISIBLE);
            else
                holder.binding.txtMonthHeader.setVisibility(View.GONE);
        } else {
            // Covers the case of data not being ready yet.
//            holder.tabItemView.setText("No tab");
//            holder.binding.setText("No Tab"));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //((TabActivity) context).deleteEntry(listEntry.get(position));
                ((TabActivity) context).editEntry(listEntry.get(position));
                return true;
            }
        });

    }

    public void setEntry(List<Entry> listEntry) {
        this.listEntry = listEntry;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (listEntry != null)
            return listEntry.size();
        else return 0;
    }
}