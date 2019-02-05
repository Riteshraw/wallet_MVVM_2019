package com.rr.project.myapplication.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TabDao {

    @Query("Select * from tab_table")
    LiveData<List<Tab>> getAllTabs();

    @Delete
    void deleteTab(Tab tab);

    @Insert
    void insertTab(Tab tab);
}
