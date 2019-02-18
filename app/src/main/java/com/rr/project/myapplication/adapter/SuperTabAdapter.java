package com.rr.project.myapplication.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rr.project.myapplication.MyHandlers;
import com.rr.project.myapplication.R;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.databinding.RecyclerviewItemBinding;

import java.util.List;

public class SuperTabAdapter extends RecyclerView.Adapter<SuperTabAdapter.SuperTabViewHolder> {
    private final LayoutInflater mInflater;
    private final Context context;
    private List<SuperTab> superTabs;

    public SuperTabAdapter(Context context) {

        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    class SuperTabViewHolder extends RecyclerView.ViewHolder {
//        private final TextView tabItemView;

        /*private SuperTabViewHolder(View itemView) {
            super(itemView);
            tabItemView = itemView.findViewById(R.id.entry);
        }*/
        private RecyclerviewItemBinding binding;

        private SuperTabViewHolder(RecyclerviewItemBinding recyclerviewItemBinding) {
            super(recyclerviewItemBinding.getRoot());
            binding = recyclerviewItemBinding;
        }
    }

    @Override
    public SuperTabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
//        return new SuperTabViewHolder(itemView);
        RecyclerviewItemBinding binding = DataBindingUtil.inflate(mInflater, R.layout.recyclerview_item, parent, false);
        binding.setHandlers(new MyHandlers());
        return new SuperTabViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SuperTabViewHolder holder, int position) {
        if (superTabs != null) {
            SuperTab current = superTabs.get(position);
//            holder.tabItemView.setText(current.getName());
            holder.binding.setSuperTab(current);
        } else {
            // Covers the case of data not being ready yet.
//            holder.tabItemView.setText("No tab");
//            holder.binding.setText("No Tab"));
        }
    }

    public void setSperTabs(List<SuperTab> superTabs) {
        this.superTabs = superTabs;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (superTabs != null)
            return superTabs.size();
        else return 0;
    }

    public void onClick(View view){
        Toast.makeText(context,"Show Tab",Toast.LENGTH_SHORT).show();
    }

}