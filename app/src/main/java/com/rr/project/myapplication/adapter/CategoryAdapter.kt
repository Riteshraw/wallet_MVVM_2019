package com.rr.project.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rr.project.myapplication.BR
import com.rr.project.myapplication.R
import com.rr.project.myapplication.dao.Category
import com.rr.project.myapplication.databinding.ItemCategoryBinding
import com.rr.project.myapplication.viewModel.CategoryViewModel

class CategoryAdapter(val context: Context?, val categoryVM: CategoryViewModel) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var mInflater: LayoutInflater
    private var categoryList: List<Category>? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = DataBindingUtil.inflate<ItemCategoryBinding>(mInflater, R.layout.item_category, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (categoryList == null) 0 else categoryList!!.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        if (categoryList != null)
            holder.bind(categoryVM, position/*,categoryList*/)
        else { // Covers the case of data not being ready yet
            Toast.makeText(context, "No Super Category present in DB", Toast.LENGTH_SHORT).show()
        }
    }

    fun setCategory(categoryList: List<Category>) {
        this.categoryList = categoryList
        this.notifyDataSetChanged()
    }

    inner class CategoryViewHolder constructor(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryVM: CategoryViewModel, position: Int) {
            binding.setVariable(BR.category, categoryList?.get(position))
            binding.setVariable(BR.position, position)
            binding.setVariable(BR.categoryVM, categoryVM)
            binding.executePendingBindings()
        }
    }

}