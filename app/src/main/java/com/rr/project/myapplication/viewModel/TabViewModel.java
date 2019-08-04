package com.rr.project.myapplication.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.repo.TabRepo;

import java.util.List;

public class TabViewModel extends AndroidViewModel {
    private LiveData<List<Tab>> listAllTabsById;
    private TabRepo tabRepo;
    private LiveData<List<Tab>> listAllTabs;
    private LiveData<Integer> tabNameCount;

    public TabViewModel(@NonNull Application application) {
        super(application);
        tabRepo = new TabRepo(application);
        listAllTabs = tabRepo.getListAllTabs();
    }

    public LiveData<List<Tab>> getAllTabs() {
        return listAllTabs;
    }

    public LiveData<Integer> isTabAlreadyPresent(Tab Tab) {
        tabNameCount = tabRepo.getAllTabsWithName(Tab);
        return tabNameCount;
    }

    public void insertTab(Tab Tab) {
        tabRepo.insertTab(Tab);
    }

    public LiveData<List<Tab>> getListAllTabsById(int superTabId) {
        listAllTabsById = tabRepo.getListAllTabsById(superTabId);
        return listAllTabsById;
    }
}