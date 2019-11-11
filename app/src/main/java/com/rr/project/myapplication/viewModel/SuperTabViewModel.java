package com.rr.project.myapplication.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rr.project.myapplication.Constant;
import com.rr.project.myapplication.TabActivity;
import com.rr.project.myapplication.WalletApplication;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.fragment.EditNameDialogFragment;
import com.rr.project.myapplication.repo.SuperTabRepo;
import com.rr.project.myapplication.repo.TabRepo;
import com.rr.project.myapplication.utils.Utils;

import java.util.List;

public class SuperTabViewModel extends AndroidViewModel {
    private final TabRepo tabRepo;
    private SuperTabRepo superTabRepo;
    private LiveData<List<SuperTab>> listAllSuperTabs;
    private LiveData<Integer> superTabNameCount;
    private Context context;

    public SuperTabViewModel(@NonNull Application application) {
        super(application);
        context = application;
        superTabRepo = new SuperTabRepo(application);
        tabRepo = new TabRepo(application);
        listAllSuperTabs = superTabRepo.getListAllSuperTabs();
    }

    public LiveData<List<SuperTab>> getAllSuperTabs() {
        return listAllSuperTabs;
    }

    public LiveData<Integer> isSuperTabAlreadyPresent(SuperTab superTab) {
        superTabNameCount = superTabRepo.getAllSuperTabsWithName(superTab);
        return superTabNameCount;
    }

    public void insertSuperTab(SuperTab superTab) {
        superTabRepo.insertSuperTab(superTab);
    }

    public void updateSuperTabDateTime(int tabId) {
        superTabRepo.updateSuperTabDateTime(tabRepo.getSuperTabId(tabId));
    }

    public void onSuperTabClick(SuperTab superTab) {
        WalletApplication.getInstance().setSuperTab(superTab);
        Intent intent = new Intent(context, TabActivity.class);
        intent.putExtra(Constant.SUPER_TAB_NAME, superTab.getName());
        intent.putExtra(Constant.SUPER_TAB_ID, superTab.getId());
        context.startActivity(intent);
    }

    public void onAddSuperTab(View view) {
        if (view.getContext() instanceof AppCompatActivity) {
            FragmentManager fm = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
            EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance(Constant.SUPER_TAB, true);
            editNameDialogFragment.show(fm, "fragment_edit_name");
        }
    }

    public void onSuperTabDwn(View view){
        Utils.downloadFile(view.getContext());
    }

    public void onSuperTabUpload(final View view){
        Utils.uploadFile(view.getContext());
    }

}