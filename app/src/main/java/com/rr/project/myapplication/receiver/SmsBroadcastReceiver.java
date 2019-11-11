package com.rr.project.myapplication.receiver;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.rr.project.myapplication.WalletApplication;
import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.viewModel.EntryViewModel;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsBroadcastReceiver";

    private String serviceProviderNumber;
    private String serviceProviderSmsCondition;

    private Listener listener;

    public SmsBroadcastReceiver() {
    }

    public SmsBroadcastReceiver(String serviceProviderNumber, String serviceProviderSmsCondition) {
        this.serviceProviderNumber = serviceProviderNumber;
        this.serviceProviderSmsCondition = serviceProviderSmsCondition;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsSender = smsMessage.getDisplayOriginatingAddress();
                    smsBody += smsMessage.getMessageBody();
                    Log.v(TAG, ""+smsBody);
                    Toast.makeText(context, "" + smsSender, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "" + smsBody, Toast.LENGTH_SHORT).show();
                }
            } else {
                Bundle smsBundle = intent.getExtras();
                if (smsBundle != null) {
                    Object[] pdus = (Object[]) smsBundle.get("pdus");
                    if (pdus == null) {
                        // Display some error to the user
                        Toast.makeText(context, "SmsBundle had no pdus key", Toast.LENGTH_SHORT).show();
                        Log.v(TAG, "SmsBundle had no pdus key");
                        return;
                    }
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBody += messages[i].getMessageBody();
                    }
                    smsSender = messages[0].getOriginatingAddress();
                }
            }

            /*if (smsSender.equals(serviceProviderNumber) && smsBody.startsWith(serviceProviderSmsCondition)) {
                if (listener != null) {
                    listener.onTextReceived(smsBody);
                }
            }*/

            if (smsSender.contains(serviceProviderNumber) &&
                    (smsBody.contains(serviceProviderSmsCondition) || smsBody.contains("credited"))) {
                /*if (listener != null) {
                    listener.onTextReceived(smsBody);
                }*/
                Toast.makeText(context, "" + smsBody, Toast.LENGTH_SHORT).show();
                addEntry(smsBody);
            }
        }
    }

    public void addEntry(String smsBody) {
        /*new EntryViewModel(WalletApplication.getInstance()).insertEntry(
                new Entry(
                        ,
                        amt,
                        smsBody.contains(serviceProviderSmsCondition) ? true : false,
                        3,//ICICI from DB
                        dateTime,
                        txt_entry_date.getText().toString(),
                        true
                ), true);*/
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onTextReceived(String text);
    }
}
