package com.rr.project.myapplication;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.rr.project.myapplication.dao.SuperTab;

import java.lang.invoke.ConstantCallSite;
import java.util.logging.Handler;

public class MyHandlers {
    public void onClick(SuperTab superTab) {
        SuperTab superTab1 = superTab;

        Intent intent = new Intent(MyHandlers.this, TabActivity.class);
        intent.putExtra(Constant.SUPER_TAB_NAME, superTab.getName());
        intent.putExtra(Constant.SUPER_TAB_ID, superTab.getId());
        this.startActivity(intent);
//        Toast.makeText(,"Test",Toast.LENGTH_SHORT).show();
        //What should be done
    }
}
