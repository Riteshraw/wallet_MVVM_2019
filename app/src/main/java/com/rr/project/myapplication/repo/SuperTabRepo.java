package com.rr.project.myapplication.repo;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.SuperTabDao;
import com.rr.project.myapplication.db.WalletRoomDB;
import com.rr.project.myapplication.utils.Utils;

import java.util.List;

public class SuperTabRepo {
    public SuperTabDao superTabDao;
    public LiveData<List<SuperTab>> listAllSuperTabs;
    public LiveData<Integer> superTabNameCount;
    public Application application;

    public SuperTabRepo(Application application) {
        this.application = application;
        WalletRoomDB db = WalletRoomDB.getDatabase(application);
        this.superTabDao = db.superTabDao();
        this.listAllSuperTabs = superTabDao.getAllSuperTabs();
        this.superTabNameCount = null;
    }

    public LiveData<List<SuperTab>> getListAllSuperTabs() {
        return listAllSuperTabs;
    }

    public void insertSuperTab(SuperTab superTab) {
//        if (!isTabAlreadyPresent(superTab))
            new insertAsyncTask(superTabDao).execute(superTab);
//        else
//            Toast.makeText(application,"Name already present",Toast.LENGTH_SHORT).show();
    }

    private static class insertAsyncTask extends AsyncTask<SuperTab, Void, Void> {
        private SuperTabDao asyncTabDao;

        public insertAsyncTask(SuperTabDao superTabDao) {
            asyncTabDao = superTabDao;
        }

        @Override
        protected Void doInBackground(SuperTab... superTabs) {
             asyncTabDao.insertSuperTab(superTabs[0]);
            return null;
        }

        /*@Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            int column = result;
        }*/
    }

    public LiveData<Integer> getAllSuperTabsWithName(SuperTab superTab) {
        superTabNameCount = superTabDao.getAllSuperTabsWithName(superTab.getName());
        return superTabNameCount;
    }

    public void updateSuperTabDateTime(int tabId){
        SuperTab superTab = superTabDao.getSuperTabFromTabId(tabId);
        superTab.setUpdateTime(Utils.getCurrentDateInMiliSecs());
        superTabDao.updateSuperTab(superTab);
    }

    /*public boolean isTabAlreadyPresent(SuperTab superTab) {
        return (superTabDao.getAllSuperTabsWithName(superTab.getName()) > 0) ? true : false;
    }*/
}
