package com.rr.project.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaCodec;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rr.project.myapplication.adapter.SuperTabAdapter;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.fragment.EditNameDialogFragment;
import com.rr.project.myapplication.view.GridDividerDecoration;
import com.rr.project.myapplication.viewModel.SuperTabViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnSuperTabClickListener {
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

    private static final int REQUEST_WRITE_PERMISSION = 786;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        sTabViewModel = ViewModelProviders.of(this).get(SuperTabViewModel.class);

        sTabViewModel.getAllSuperTabs().observe(this, new Observer<List<SuperTab>>() {
            @Override
            public void onChanged(@Nullable List<SuperTab> entries) {
                sTabAdapter.setSperTabs(entries);
            }
        });


        sTabAdapter = new SuperTabAdapter(this, new OnSuperTabClickListener() {
            @Override
            public void onSuperTabClick(SuperTab superTab) {
                WalletApplication.getInstance().setSuperTab(superTab);

                Intent intent = new Intent(context, TabActivity.class);
                intent.putExtra(Constant.SUPER_TAB_NAME, superTab.getName());
                intent.putExtra(Constant.SUPER_TAB_ID, superTab.getId());
                startActivity(intent);
//                Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
            }
        });
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

        /*new MyHandlers().setListener(new MyHandlers.OnSuperTabClickListener() {
            @Override
            public void onSuperTabClick(SuperTab superTab) {
                Intent intent = new Intent(context, TabActivity.class);
                intent.putExtra(Constant.SUPER_TAB_NAME, superTab.getName());
                intent.putExtra(Constant.SUPER_TAB_ID, superTab.getId());
                startActivity(intent);
                Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
            }
        });*/

//        showEditDialog();


    }

    @OnClick(R.id.fab_download)
    public void submit1() {
        requestPermission();

        try {
//            File sd = Environment.getExternalStorageDirectory();
            File sd = new File(Environment.getExternalStorageDirectory() + File.separator + "Wallet BackUp");
            sd.mkdirs();

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/wallet_db";
                String backupDBPath = "wallet_db.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/wallet_db-shm";
                String backupDBPath = "wallet_db-shm";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/wallet_db-wal";
                String backupDBPath = "wallet_db-wal";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.fab_upload)
    public void submit2() {
        requestPermission();

        try {
//            File sd = Environment.getExternalStorageDirectory();
            File sd = new File(Environment.getExternalStorageDirectory() + File.separator + "Wallet BackUp");
            sd.mkdirs();

            if (sd.canWrite()) {
                String backupDBPath = "/data/data/" + getPackageName() + "/databases/wallet_db";
                String currentDBPath = "wallet_db.db";
                File currentDB = new File(sd, currentDBPath);
                File backupDB = new File(backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
            if (sd.canWrite()) {
                String backupDBPath = "/data/data/" + getPackageName() + "/databases/wallet_db-shm";
                String currentDBPath = "wallet_db-shm";
                File currentDB = new File(sd, currentDBPath);
                File backupDB = new File(backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
            if (sd.canWrite()) {
                String backupDBPath = "/data/data/" + getPackageName() + "/databases/wallet_db-wal";
                String currentDBPath = "wallet_db-wal";
                File currentDB = new File(sd, currentDBPath);
                File backupDB = new File(backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void addSuperTab(View view) {
//        sTabViewModel.isSuperTabAlreadyPresent(new SuperTab("test", new Date().getTime()));
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance(Constant.SUPER_TAB, true);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    /*private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }*/

    @Override
    public void onSuperTabClick(SuperTab superTab) {
        Intent intent = new Intent(context, TabActivity.class);
        intent.putExtra(Constant.SUPER_TAB_NAME, superTab.getName());
        intent.putExtra(Constant.SUPER_TAB_ID, superTab.getId());
        startActivity(intent);
//        Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permissions granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
//            openFilePicker();
            Toast.makeText(context, "Permissions already granted", Toast.LENGTH_SHORT).show();
        }
    }
}

