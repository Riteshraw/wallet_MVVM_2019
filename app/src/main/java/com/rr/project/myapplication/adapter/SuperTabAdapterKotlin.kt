package com.rr.project.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rr.project.myapplication.BR
import com.rr.project.myapplication.R
import com.rr.project.myapplication.dao.SuperTab
import com.rr.project.myapplication.databinding.RecyclerviewItemBinding
import com.rr.project.myapplication.viewModel.SuperTabViewModel

private class SuperTabAdapterKotlin(private val context: Context, private val viewModel: SuperTabViewModel) : RecyclerView.Adapter<SuperTabAdapterKotlin.SuperTabViewHolder>() {
    private val mInflater: LayoutInflater
    private var superTabs: List<SuperTab>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperTabViewHolder {
        val binding = DataBindingUtil.inflate<RecyclerviewItemBinding>(mInflater, R.layout.recyclerview_item, parent, false)
        return SuperTabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SuperTabViewHolder, position: Int) {
        if (superTabs != null) {
            holder.bind(viewModel, position)
        } else { // Covers the case of data not being ready yet
            Toast.makeText(context, "No Super Tabs present in DB", Toast.LENGTH_SHORT).show()
        }
    }

    fun setSuperTabs(superTabs: List<SuperTab>?) {
        this.superTabs = superTabs
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (superTabs == null) 0 else superTabs!!.size
    }

    internal inner class SuperTabViewHolder(private val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(superTabViewModel: SuperTabViewModel, position: Int) {
            binding.setVariable(BR.superTab, superTabs!![position])
            binding.setVariable(BR.position, position)
            binding.setVariable(BR.superTabVM, superTabViewModel)
            binding.executePendingBindings()
        }
    }

    init {
        mInflater = LayoutInflater.from(context)
    }
}