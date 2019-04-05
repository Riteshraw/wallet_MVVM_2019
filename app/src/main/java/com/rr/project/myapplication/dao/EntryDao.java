package com.rr.project.myapplication.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EntryDao {

    @Query("Select * from entry_table")
    LiveData<List<Entry>> getAllEntries();

    @Insert
    long insert(Entry entry);

    @Query("Select count(*) from tab_table where tabName= :tabName")
    LiveData<Integer> getAllTabsWithName(String tabName);

    @Query("Select count(*) from entry_table where id= :tabId")
    LiveData<List<Entry>> getListAllEntryByTabId(int tabId);
}
