package com.rr.project.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoryDao {

    @Query("Select * from category_table order by updateTime desc")
    fun getAllCatergories(): LiveData<List<Category?>?>?

    @Delete
    fun deleteCategory(category: Category?)

    @Insert
    fun insertCategory(category: Category?)

    @Update
    fun updateCategory(category: Category?)

    @Query("Select count(*) from category_table where catName= :catName")
    fun getAllCategoryWithName(catName: String): LiveData<Int?>?

    @Query("Select * from category_table where id= :id")
    fun getCategoryFromId(id: Int): LiveData<Category?>?

}