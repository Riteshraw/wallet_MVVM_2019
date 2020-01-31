package com.rr.project.myapplication.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;

import com.rr.project.myapplication.dao.Category;
import com.rr.project.myapplication.dao.CategoryDao;
import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.EntryDao;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.SuperTabDao;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.dao.TabDao;

@Database(entities = {Tab.class, Entry.class, SuperTab.class/*, Category.class*/}, version = 1)
public abstract class WalletRoomDB extends RoomDatabase {

    public abstract TabDao tabDao();

    public abstract SuperTabDao superTabDao();

    public abstract EntryDao entryDao();

//    public abstract CategoryDao categoryDao();

    public static volatile WalletRoomDB INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE category_table " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, catName TEXT NOT NULL, updateTime INTEGER NOT NULL)");
            database.execSQL("ALTER TABLE entry_table ADD COLUMN superTabId INTEGER DEFAULT 0 NOT NULL");
            database.execSQL("ALTER TABLE entry_table ADD COLUMN superTabName TEXT");
            database.execSQL("ALTER TABLE superTab_table ADD COLUMN isCategory Boolean");
        }
    };

    public static WalletRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WalletRoomDB.class) {
                if (INSTANCE == null) {
                    Builder<WalletRoomDB> wallet_db = Room.databaseBuilder(context, WalletRoomDB.class, "wallet_db");
                    wallet_db.allowMainThreadQueries();
                    INSTANCE = wallet_db/*.addMigrations(MIGRATION_1_2)*/.build();
                }
            }
        }
        return INSTANCE;
    }
}
