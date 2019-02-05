package com.rr.project.myapplication.dao;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "entry_table",
        foreignKeys = @ForeignKey(
                entity = Tab.class,
                parentColumns = "id",
                childColumns = "tabId"))

public class Entry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    int tabId;
    private String note;
    private String amount;
    private boolean isDebit;
    private String balance;

    private long date;

    public Entry(/*int id,*/ String note, String amount, boolean isDebit, int tabId, long date) {
//        this.id = id;
        this.note = note;
        this.amount = amount;
        this.isDebit = isDebit;
        this.tabId = tabId;
        this.date = date;
    }

    public Entry() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isDebit() {
        return isDebit;
    }

    public void setDebit(boolean debit) {
        isDebit = debit;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }


}
