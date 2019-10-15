package com.rr.project.myapplication;

import android.Manifest;
import android.app.Application;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.Tab;

public class WalletApplication extends Application {
    private static final int SMS_PERMISSION_CODE = 4564;
    private static WalletApplication appInstance;
    private SuperTab superTab;
    private Tab tab;
    private boolean isEditEntry;

    @Override
    public void onCreate() {
        super.onCreate();
        SMSReceiver receiver = new SMSReceiver();
        IntentFilter intentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        this.registerReceiver(receiver, intentFilter);
    }

    public static WalletApplication getInstance() {
        if (appInstance == null)
            appInstance = new WalletApplication();

        return appInstance;
    }

    private WalletApplication() {
        appInstance = this;
    }

    public SuperTab getSuperTab() {
        return superTab;
    }

    public void setSuperTab(SuperTab superTab) {
        this.superTab = superTab;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public boolean isEditEntry() {
        return isEditEntry;
    }

    public void setEditEntry(boolean editEntry) {
        isEditEntry = editEntry;
    }

   /*public static LoginRepo getLoginRepo() {
        return LoginRepo.getInstance();
    }*/
}
