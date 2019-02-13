package com.rr.project.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rr.project.myapplication.adapter.SuperTabAdapter;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.fragment.EditNameDialogFragment;
import com.rr.project.myapplication.view.GridDividerDecoration;
import com.rr.project.myapplication.view.GridDividerItemDecoration;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private SuperTabViewModel sTabViewModel;
    private SuperTabAdapter sTabAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

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
        recyclerView.setAdapter(sTabAdapter);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                ((GridLayoutManager) layoutManager).getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        //Check this link for gridlayout item spacing
        //https://www.dev2qa.com/android-recyclerview-example/

        // Use gird or staggered grid layout item divider.
//        GridDividerItemDecoration gridItemDivider = new GridDividerItemDecoration(5,5,2);
//        GridDividerDecoration gridItemDivider1 = new GridDividerDecoration(getApplicationContext());
        GridDividerDecoration gridItemDivider2 = new GridDividerDecoration(getApplicationContext());
        recyclerView.addItemDecoration(gridItemDivider2);
        recyclerView.setLayoutManager(layoutManager);

//        showEditDialog();
    }

    public void addSuperTab(View view) {
//        sTabViewModel.insertSuperTab(new SuperTab("test", new Date().getTime()));
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

}
