package com.rr.project.myapplication;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.rr.project.myapplication.adapter.SuperTabAdapter;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.databinding.ActivityMainBinding;
import com.rr.project.myapplication.fragment.EditNameDialogFragment;
import com.rr.project.myapplication.utils.Constants;
import com.rr.project.myapplication.utils.Utils;
import com.rr.project.myapplication.viewModel.SuperTabViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private SuperTabViewModel sTabViewModel;
    private SuperTabAdapter sTabAdapter;
    private Context context;

    /*You can enhance it when using Android databinding like this:
    Define custom binding adapter:

    @BindingAdapter({"digitsBeforeZero", "digitsAfterZero"})
    public void bindAmountInputFilter(EditText view, int digitsBeforeZero, int digitsAfterZero) {
        view.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(digitsBeforeZero, digitsAfterZero)});
    }

    Add attributes to EditText:
    app:digitsBeforeZero="@{7}"
    app:digitsAfterZero="@{2}"
    and it will automatically set the input filter for the edittext*/

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        sTabViewModel = ViewModelProviders.of(this).get(SuperTabViewModel.class);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        sTabViewModel.getAllSuperTabs().observe(this, new Observer<List<SuperTab>>() {
            @Override
            public void onChanged(@Nullable List<SuperTab> entries) {
                sTabAdapter.setSuperTabs(entries);
            }
        });

        binding.setVariable(BR.superTabVM, sTabViewModel);

        sTabAdapter = new SuperTabAdapter(this, sTabViewModel);
        recyclerView.setAdapter(sTabAdapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//        ((GridLayoutManager) layoutManager).getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        //Check this link for gridlayout item spacing
        //https://www.dev2qa.com/android-recyclerview-example/
//        GridDividerDecoration gridItemDivider2 = new GridDividerDecoration(getApplicationContext());
//        recyclerView.addItemDecoration(gridItemDivider2);
        recyclerView.setLayoutManager(layoutManager);

    }

    @OnClick(R.id.fab_download)
    public void submit1() {
        requestPermission();
        Utils.downloadFile(this);
    }

    @OnClick(R.id.fab_upload)
    public void submit2() {
        requestPermission();
        Utils.uploadFile(this);
    }

    /*public void addSuperTab(View view) {
//        sTabViewModel.isSuperTabAlreadyPresent(new SuperTab("test", new Date().getTime()));
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance(Constant.SUPER_TAB, true);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }*/

    /*private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }*/

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS},
                    Constants.REQUEST_PERMISSION);
        } else {
//            openFilePicker();
            Toast.makeText(context, "Permissions already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.REQUEST_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permissions granted", Toast.LENGTH_SHORT).show();
        }
    }
}

