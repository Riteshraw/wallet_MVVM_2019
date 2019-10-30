package com.rr.project.myapplication.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.repo.EntryRepo;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {

    private LiveData<List<Entry>> listAllEntries;
    private LiveData<List<Entry>> listAllEntriesById;
    private EntryRepo entryRepo;
    private LiveData<Integer> tabNameCount;
    private SuperTabViewModel superTabViewModel;

    public EntryViewModel(@NonNull Application application) {
        super(application);
        entryRepo = new EntryRepo(application);
        listAllEntries = entryRepo.getListEntries();
        superTabViewModel = new SuperTabViewModel(application);
    }

    public LiveData<List<Entry>> getAllEntries() {
        return listAllEntries;
    }

    public LiveData<List<Entry>> getListAllEntriesById(int tabId) {
        listAllEntriesById = entryRepo.getListAllEntryByTabId(tabId);
        return listAllEntriesById;
    }

    public LiveData<Integer> isSuperAlreadyPresent(Tab tab) {
        tabNameCount = entryRepo.getAllTabsWithName(tab);
        return tabNameCount;
    }

    public void insertEntry(Entry entry, boolean isCurrentDateEntry) {
        entryRepo.insertEntry(entry, isCurrentDateEntry);
        updateSuperTabDateTime(entry.getTabId());
    }

    public void updateEntry(Entry editEntry, boolean isDateChange, long originalDate) {
        entryRepo.updateEntryWithAmtOrDateChangeByDelete(editEntry, isDateChange, originalDate);
        updateSuperTabDateTime(editEntry.getTabId());
    }

    public void updateEntryNote(Entry editEntry) {
        entryRepo.updateEntry(editEntry);
        updateSuperTabDateTime(editEntry.getTabId());
    }

    public void deleteEntry(Entry entry) {
        entryRepo.deleteEntry(entry, 0);
    }

    private void updateSuperTabDateTime(int tabId) {
        superTabViewModel.updateSuperTabDateTime(tabId);
    }

}
