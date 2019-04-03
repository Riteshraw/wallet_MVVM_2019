package com.rr.project.myapplication.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.repo.EntryRepo;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {

    private final LiveData<List<Entry>> listAllEntries;
    private final EntryRepo entryRepo;
    private LiveData<Integer> tabNameCount;

    public EntryViewModel(@NonNull Application application) {
        super(application);
        entryRepo = new EntryRepo(application);
        listAllEntries = entryRepo.getListEntries();
    }

    public LiveData<List<Entry>> getAllEntries() {
        return listAllEntries;
    }

    public void insertEntry(Entry entry) {
        entryRepo.insertEntry(entry);
    }

    public LiveData<Integer> isSuperAlreadyPresent(Tab tab) {
        tabNameCount = entryRepo.getAllTabsWithName(tab);
        return tabNameCount;
    }
}
