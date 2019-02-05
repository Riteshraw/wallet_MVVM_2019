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
}
