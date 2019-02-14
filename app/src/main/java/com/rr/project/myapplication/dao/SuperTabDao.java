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

    @Query("Select * from superTab_table")
    LiveData<List<SuperTab>> getAllSuperTabs();

    @Delete
    void deleteSuperTab(SuperTab superTab);

    @Insert
    void insertSuperTab(SuperTab superTab);

    @Update
    void updateSuperTab(SuperTab superTab);

    @Query("Select count(*) from superTab_table where name= :superTabName")
    LiveData<Integer> getAllSuperTabsWithName(String superTabName);

}
