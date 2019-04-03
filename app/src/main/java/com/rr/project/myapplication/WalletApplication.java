package com.rr.project.myapplication;

import android.app.Application;

import com.rr.project.myapplication.dao.SuperTab;
import com.rr.project.myapplication.dao.Tab;

public class WalletApplication extends Application {
    private static WalletApplication appInstance;
    private SuperTab superTab;
    private Tab tab;

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

    /*public static LoginRepo getLoginRepo() {
        return LoginRepo.getInstance();
    }*/
}
