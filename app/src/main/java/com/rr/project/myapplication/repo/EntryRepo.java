package com.rr.project.myapplication.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.Toast;

import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.EntryDao;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.db.WalletRoomDB;

import java.util.List;

public class EntryRepo {
    private EntryDao entryDao;
    private LiveData<List<Entry>> listEntries;
    private LiveData<List<Entry>> listEntriesById;
    public LiveData<Integer> tabNameCount;

    public EntryRepo(Application application) {
        WalletRoomDB db = WalletRoomDB.getDatabase(application);
        entryDao = db.entryDao();
        listEntries = entryDao.getAllEntries();
    }

    public LiveData<List<Entry>> getListEntries() {
        return listEntries;
    }

    public void insertEntry(Entry entry) {
        new insertAynscTask().execute(entry);
    }

    public LiveData<Integer> getAllTabsWithName(Tab tab) {
        tabNameCount = entryDao.getAllTabsWithName(tab.getTabName());
        return tabNameCount;
    }

    private class insertAynscTask extends AsyncTask<Entry, Void, Long> {

        @Override
        protected Long doInBackground(Entry... entries) {
            return entryDao.insert(entries[0]);
        }
    }

    public LiveData<List<Entry>> getListAllEntryByTabId(int tabId) {
        listEntriesById = entryDao.getListAllEntryByTabId(tabId);
        return listEntriesById;
    }
}
