package com.rr.project.myapplication.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.EntryDao;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.SuperTabDao;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.dao.TabDao;

@Database(entities = {Tab.class, Entry.class, SuperTab.class}, version = 1)
public abstract class WalletRoomDB extends RoomDatabase {

    public abstract TabDao tabDao();

    public abstract SuperTabDao superTabDao();

    public abstract EntryDao entryDao();

    public static volatile WalletRoomDB INSTANCE;

    public static WalletRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WalletRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, WalletRoomDB.class,"wallet_db").
                            allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
