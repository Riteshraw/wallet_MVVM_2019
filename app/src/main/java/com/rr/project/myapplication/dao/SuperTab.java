package com.rr.project.myapplication.dao;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "superTab_table")
public class SuperTab {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private long updateTime;

    public SuperTab() {
    }

    public SuperTab(int id, String tabName, long date) {
        this.id = id;
        this.name = tabName;
        this.updateTime = date;
    }

    public SuperTab(String tabName, long date) {
        this.name = tabName;
        this.updateTime = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
