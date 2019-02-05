package com.rr.project.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rr.project.myapplication.adapter.SuperTabAdapter;
import com.rr.project.myapplication.dao.SuperTab;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private SuperTabViewModel sTabViewModel;
    private SuperTabAdapter sTabAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sTabViewModel = ViewModelProviders.of(this).get(SuperTabViewModel.class);

        sTabViewModel.getAllSuperTabs().observe(this, new Observer<List<SuperTab>>() {
            @Override
            public void onChanged(@Nullable List<SuperTab> entries) {
                sTabAdapter.setSperTabs(entries);
            }
        });

        sTabAdapter = new SuperTabAdapter(this);
        recyclerview.setAdapter(sTabAdapter);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addSuperTab(View view) {
        sTabViewModel.insertSuperTab(new SuperTab("test",new Date().getTime()));
    }
}
