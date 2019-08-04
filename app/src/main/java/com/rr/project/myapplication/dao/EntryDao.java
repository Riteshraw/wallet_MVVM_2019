package com.rr.project.myapplication.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EntryDao {

    @Query("Select * from entry_table")
    LiveData<List<Entry>> getAllEntries();

    @Insert
    long insert(Entry entry);

    @Update
    public int updateEntry(Entry... lastEntry);

    @Delete
    void deleteEntry(Entry entry);

    @Query("Select count(*) from tab_table where tabName= :tabName")
    LiveData<Integer> getAllTabsWithName(String tabName);

    @Query("Select * from entry_table where tabId = :tabId order by entryTime DESC ")
    LiveData<List<Entry>> getListAllEntryByTabId(int tabId);

    @Query("Select * from entry_table where tabId = :tabId order by entryTime DESC limit 1")
    Entry getLastEntryById(int tabId);

    @Query("Select * from entry_table where tabId = :tabId and isLatestEntry = 0 order by entryTime DESC limit 1")
    Entry getLastEntryByIdForUpdateCase(int tabId);

    @Query("Select * from entry_table where tabId = :tabId and dateString = :dateString order by entryTime DESC limit 1")
    Entry getLastEntryByTabIdAndDate(int tabId, String dateString);

    @Query("Select * from entry_table where tabId = :tabId and entryTime < :entryTime order by entryTime DESC limit 1")
    Entry getPrevEntryByTabIdAndDate(int tabId, long entryTime);

    @Query("Select * from entry_table where tabId = :tabId and id != :id and dateString = :dateString order by entryTime DESC limit 1")
    Entry getLastEntryByTabIdAndDateForSameAndDateUpdateCase(int tabId, int id, String dateString);

    @Query("Select * from entry_table where tabId = :tabId and id != :id and entryTime < :entryTime order by entryTime DESC limit 1")
    Entry getLastEntryByTabIdAndDateForNotSameAndDateUpdateCase(int tabId, int id, long entryTime);

    @Query("Select * from entry_table where tabId = :tabId and entryTime > :entryTime order by entryTime ASC limit 1")
    Entry getOneUpEntryByDateTime(int tabId, long entryTime);

}
