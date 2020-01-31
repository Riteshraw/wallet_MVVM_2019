package com.rr.project.myapplication.viewModel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rr.project.myapplication.Constant
import com.rr.project.myapplication.TabActivity
import com.rr.project.myapplication.WalletApplication
import com.rr.project.myapplication.dao.Category
import com.rr.project.myapplication.repo.CategoryRepo
import java.util.*

class CategoryViewModel constructor(application: Application) : AndroidViewModel(application) {
    private var categoryRepo: CategoryRepo
    private var listAllCategories: LiveData<List<Category>>
    private var superTabNameCount: LiveData<Int>? = null
    private val context: Context

    init {
        categoryRepo = CategoryRepo(application)
        listAllCategories = categoryRepo.getAllCategories()
        context = application
    }

    fun getAllCategories() = listAllCategories

    fun onCategoryClick(category: Category) {
        /*WalletApplication.getInstance().category = category
        val intent: Intent = Intent(context, TabActivity::class.java).also {
            it.putExtra(Constant.SUPER_CATEGORY_NAME, category.catName)
            it.putExtra(Constant.SUPER_CATEGORY_ID, category.id)
        }
        context.startActivity(intent)*/
    }

    fun onAddCategoryClick(view: View) {
        /*if (view.getContext() is AppCompatActivity) {
            val fm = (view.getContext() as AppCompatActivity).supportFragmentManager
            val editNameDialogFragment = EditNameDialogFragment.newInstance(Constant.SUPER_TAB, true)
            editNameDialogFragment.show(fm, "fragment_edit_name")
        }*/
//        insertCategory(Category(1, "Paytm", Date().time))
    }

    fun insertCategory(category: Category) {
        categoryRepo.insertCategory(category)
    }

}