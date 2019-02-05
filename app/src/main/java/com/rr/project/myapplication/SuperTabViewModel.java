package com.rr.project.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.rr.project.myapplication.dao.SuperTab;

import java.util.List;

public class SuperTabViewModel extends AndroidViewModel {
    private SuperTabRepo tabRepo;
    private LiveData<List<SuperTab>> listAllSuperTabs;

    public SuperTabViewModel(@NonNull Application application) {
        super(application);
        tabRepo = new SuperTabRepo(application);
        listAllSuperTabs = tabRepo.getListAllSuperTabs();
    }

    public LiveData<List<SuperTab>> getAllSuperTabs() {
        return listAllSuperTabs;
    }

    public void insertSuperTab(SuperTab superTab) {
        tabRepo.insertSuperTab(superTab);
    }

}