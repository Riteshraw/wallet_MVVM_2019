package com.rr.project.myapplication;

import android.app.Application;
import android.content.IntentFilter;
import android.provider.Telephony;

import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.receiver.SMSReceiver;
import com.rr.project.myapplication.receiver.SmsBroadcastReceiver;
import com.rr.project.myapplication.utils.Constants;

public class WalletApplication extends Application {
    private static final int SMS_PERMISSION_CODE = 4564;
    private static WalletApplication appInstance;
    private SuperTab superTab;
    private Tab tab;
    private boolean isEditEntry;
    private SmsBroadcastReceiver smsBroadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
//        SMSReceiver receiver = new SMSReceiver();
//        IntentFilter intentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
//        this.registerReceiver(receiver, intentFilter);

        smsBroadcastReceiver = new SmsBroadcastReceiver(/*Constants.SERVICE_NUMBER, Constants.SERVICE_CONDITION*/);
        registerReceiver(smsBroadcastReceiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
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
   @Override
   public void onTerminate() {
       unregisterReceiver(smsBroadcastReceiver);
       super.onTerminate();
   }
}
