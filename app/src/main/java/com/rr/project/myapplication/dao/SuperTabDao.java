package com.rr.project.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SuperTabDao {

    @Query("Select * from superTab_table order by updateTime desc")
    LiveData<List<SuperTab>> getAllSuperTabs();

    @Delete
    void deleteSuperTab(SuperTab superTab);

    @Insert
    void insertSuperTab(SuperTab superTab);

    @Update
    void updateSuperTab(SuperTab superTab);

    @Query("Select count(*) from superTab_table where name= :superTabName")
    LiveData<Integer> getAllSuperTabsWithName(String superTabName);

    @Query("Select * from superTab_table where id= :id")
    SuperTab getSuperTabFromTabId(int id);
}
