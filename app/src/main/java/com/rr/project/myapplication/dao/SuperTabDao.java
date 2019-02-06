package com.rr.project.myapplication.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SuperTabDao {

    @Query("Select * from superTab_table Order By updateTime ASC")
    LiveData<List<SuperTab>> getAllSuperTabs();

    @Delete
    void deleteSuperTab(SuperTab superTab);

    @Insert
    void insertSuperTab(SuperTab superTab);

    //Need to update the updateTime every time a entry is made so that while fetching super tab list
    //the entry made against the super tab is first
    //@Update
    //void updateSuperTab(SuperTab superTab);
    @Query("UPDATE superTab_table SET updateTime = :time WHERE id = :id")
    void update(long time, int id);

}
