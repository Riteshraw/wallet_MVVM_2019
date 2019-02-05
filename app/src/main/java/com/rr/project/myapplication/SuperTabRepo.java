package com.rr.project.myapplication;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.SuperTabDao;
import com.rr.project.myapplication.db.WalletRoomDB;

import java.util.List;

public class SuperTabRepo {
    public SuperTabDao superTabDao;
    public LiveData<List<SuperTab>> listAllSuperTabs;

    public SuperTabRepo(Application application) {
        WalletRoomDB db = WalletRoomDB.getDatabase(application);
        this.superTabDao = db.superTabDao();
        this.listAllSuperTabs = superTabDao.getAllSuperTabs();
    }

    public LiveData<List<SuperTab>> getListAllSuperTabs() {
        return listAllSuperTabs;
    }

    public void insertSuperTab(SuperTab superTab) {
        new insertAsyncTask(superTabDao).execute(superTab);
    }

    private static class insertAsyncTask extends AsyncTask<SuperTab,Void,Void> {
        private SuperTabDao asyncTabDao;

        public insertAsyncTask(SuperTabDao superTabDao) {
            asyncTabDao = superTabDao;
        }

        @Override
        protected Void doInBackground(SuperTab... superTabs) {
            asyncTabDao.insertSuperTab(superTabs[0]);
            return null;
        }
    }
}
