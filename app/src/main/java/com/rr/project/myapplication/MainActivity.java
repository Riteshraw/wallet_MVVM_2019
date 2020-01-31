package com.rr.project.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.rr.project.myapplication.databinding.ActivityMainBinding;
import com.rr.project.myapplication.fragment.FragmentCategory;
import com.rr.project.myapplication.fragment.FragmentSuperTab;
import com.rr.project.myapplication.utils.Constants;
import com.rr.project.myapplication.viewModel.SuperTabViewModel;


public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        context = this;
        requestPermission();

        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVariable(com.rr.project.myapplication.BR.superTabVM, new SuperTabViewModel(getApplication()));

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_tab:
                        openFragment(new FragmentSuperTab());
                        return true;
                    case R.id.navigation_categry:
                        openFragment(new FragmentSuperTab());
//                        openFragment(new FragmentCategory());
                        return true;
                }
                return false;
            }
        });

        openFragment(new FragmentSuperTab());

    }

    private void openFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.commit();
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_SMS,
                            Manifest.permission.RECEIVE_SMS
                    },
                    Constants.REQUEST_PERMISSION);
        } else {
            //Toast.makeText(context, "Permissions already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.REQUEST_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(context, "Permissions granted", Toast.LENGTH_SHORT).show();
        }
    }
}

