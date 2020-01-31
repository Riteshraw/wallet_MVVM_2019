package com.rr.project.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rr.project.myapplication.BR
import com.rr.project.myapplication.R
import com.rr.project.myapplication.adapter.CategoryAdapter
import com.rr.project.myapplication.dao.Category
import com.rr.project.myapplication.databinding.FragmentCategoryBinding
import com.rr.project.myapplication.viewModel.CategoryViewModel

class FragmentCategory() : Fragment() {
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val categoryVM = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        val binding: FragmentCategoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        val recyclerView: RecyclerView? = binding.recyclerView

        binding.setVariable(BR.categoryVM, categoryVM)

        adapter = CategoryAdapter(context,categoryVM)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = GridLayoutManager(context, 2)

        categoryVM.getAllCategories()?.observe(this, Observer<List<Category>> { category ->
            adapter.setCategory(category)
        })

        return binding.root
    }
}