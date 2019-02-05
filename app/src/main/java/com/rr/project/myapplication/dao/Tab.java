package com.rr.project.myapplication.dao;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tab_table",
        foreignKeys = @ForeignKey(
                entity = SuperTab.class,
                parentColumns = "id",
                childColumns = "superTabId"))

public class Tab {
    @PrimaryKey(autoGenerate = true)
    int id;
    int superTabId;
    String tabName;
    long updateTime;

    public Tab() {
    }

    public Tab(int id, String tabName, long date) {
        this.id = id;
        this.tabName = tabName;
        this.updateTime = date;
    }

    public Tab(String tabName, long date) {
        this.tabName = tabName;
        this.updateTime = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTabName() {
        return tabName;
    }

    public int getSuperTabId() {
        return superTabId;
    }

    public void setSuperTabId(int superTabId) {
        this.superTabId = superTabId;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

}
