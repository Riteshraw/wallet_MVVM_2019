package com.rr.project.myapplication.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.rr.project.myapplication.dao.Category
import com.rr.project.myapplication.dao.CategoryDao
import com.rr.project.myapplication.db.WalletRoomDB

class CategoryRepo(application: Application) {
    private lateinit var catDao: CategoryDao
    private lateinit var listAllCategories: LiveData<List<Category>>

    init {
//        catDao = WalletRoomDB.getDatabase(application).categoryDao()
//        listAllCategories = catDao.getAllCatergories()
    }

    fun getAllCategories() = listAllCategories

    fun insertCategory(category: Category) {
//        catDao.insertCategory(category)
    }

}