package com.rr.project.myapplication.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.dao.TabDao;
import com.rr.project.myapplication.db.WalletRoomDB;

import java.util.List;

public class TabRepo {
    public TabDao tabDao;
    public LiveData<List<Tab>> listAllTabs,listAllTabsById;
    public LiveData<Integer> tabNameCount;
    public Application application;

    public TabRepo(Application application) {
        this.application = application;
        WalletRoomDB db = WalletRoomDB.getDatabase(application);
        this.tabDao = db.tabDao();
        this.listAllTabs = tabDao.getAllTabs();
        this.tabNameCount = null;
    }

    public LiveData<List<Tab>> getListAllTabs() {
        return listAllTabs;
    }

    public LiveData<List<Tab>> getListAllTabsById(int superTabId) {
        this.listAllTabsById = tabDao.getAllTabsById(superTabId);
        return listAllTabsById;
    }

    public void insertTab(Tab Tab) {
            new insertAsyncTask(tabDao).execute(Tab);
    }

    private static class insertAsyncTask extends AsyncTask<Tab, Void, Void> {
        private TabDao asyncTabDao;

        public insertAsyncTask(TabDao TabDao) {
            asyncTabDao = TabDao;
        }

        @Override
        protected Void doInBackground(Tab... Tabs) {
             asyncTabDao.insertTab(Tabs[0]);
            return null;
        }

    }

    public LiveData<Integer> getAllTabsWithName(Tab tab) {
        tabNameCount = tabDao.getAllTabsWithName(tab.getTabName());
        return tabNameCount;
    }

}
