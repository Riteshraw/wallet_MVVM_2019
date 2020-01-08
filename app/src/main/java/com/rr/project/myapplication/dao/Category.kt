package com.rr.project.myapplication.dao

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "category_table")
data class Category(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val catName: String,
        val updateTime: Long
) {

    fun getFormattedUpdateTime() = SimpleDateFormat("dd/MM/yyyy hh:mm").format(Date(updateTime))

}