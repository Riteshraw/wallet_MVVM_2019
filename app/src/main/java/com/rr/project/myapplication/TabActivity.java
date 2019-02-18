package com.rr.project.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        //Make view pager
        //Check for tabs in DB under Super tab from getExtra through intent
        //populate pager adapter if no. tabs > 0
        //Make use of fragments for tabs
    }
}
