package com.rr.project.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TabDao {

    @Query("Select * from tab_table")
    LiveData<List<Tab>> getAllTabs();

    @Delete
    void deleteTab(Tab tab);

    @Insert
    void insertTab(Tab tab);

    @Query("Select count(*) from tab_table where tabName=:tabName and superTabId=:superTabId")
    LiveData<Integer> getAllTabsWithName(String tabName, int superTabId);

    @Query("Select * from tab_table where superTabId= :superTabId")
    LiveData<List<Tab>> getAllTabsById(int superTabId);

    @Query("Select superTabId from tab_table where id= :tabId")
    int getSuperTabId(int tabId);
}
