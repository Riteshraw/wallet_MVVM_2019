package com.rr.project.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.rr.project.myapplication.adapter.SuperTabAdapter;
import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.fragment.EditNameDialogFragment;
import com.rr.project.myapplication.view.GridDividerDecoration;
import com.rr.project.myapplication.viewModel.SuperTabViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnSuperTabClickListener {
    private SuperTabViewModel sTabViewModel;
    private SuperTabAdapter sTabAdapter;
    private Context context;

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
                Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(sTabAdapter);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
        //((GridLayoutManager) layoutManager).getOrientation());
        //recyclerView.addItemDecoration(dividerItemDecoration);
        //Check this link for gridlayout item spacing
        //https://www.dev2qa.com/android-recyclerview-example/

        GridDividerDecoration gridItemDivider2 = new GridDividerDecoration(getApplicationContext());
        recyclerView.addItemDecoration(gridItemDivider2);
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
        Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
    }
}
