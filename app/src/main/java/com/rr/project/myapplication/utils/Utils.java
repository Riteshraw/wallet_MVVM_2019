package com.rr.project.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import androidx.appcompat.app.AlertDialog;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rr.project.myapplication.MainActivity;
import com.rr.project.myapplication.dao.Entry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private static Calendar cal = Calendar.getInstance();

    public static String getCurrentDateInString() {
        return sdf.format(new Date().getTime());
    }

    public static long getDateInMiliSecs(String date) {
        try {
//            return new Date(date).getTime();
            return sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Date getDateFromString(String date) {
        try {
//            return new Date(date).getTime();
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getDateInMiliSecsForBackEntry(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateFromString(date));
//        calendar.add(Calendar.HOUR_OF_DAY, 5);
//        calendar.add(Calendar.MINUTE, 31);
        return calendar.getTimeInMillis();
    }

    public static boolean dateStringIsCurrentDate(String date) {
        return getCurrentDateInString().equals(date);
    }

    public static String getTextFromEditText(EditText editText) {
        return editText.getText().toString();
    }

    public static int getMonthFromMilisecs(long milliseconds) {
        cal.setTimeInMillis(milliseconds);
//        int mYear = cal.get(Calendar.YEAR);
//        int mMonth = cal.get(Calendar.MONTH);
//        int mDay = cal.get(Calendar.DAY_OF_MONTH);
//        int hr = cal.get(Calendar.HOUR);
//        int min = cal.get(Calendar.MINUTE);
//        int sec = cal.get(Calendar.SECOND);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getYearFromMilisecs(long milliseconds) {
        cal.setTimeInMillis(milliseconds);
        return cal.get(Calendar.YEAR);
    }

    public static void hideKeyboard(Activity context) {
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public static long getCurrentDateInMiliSecs() {
        return new Date().getTime();
    }

    public static long getEntryTime(TextView textView) {
        return Utils.getCurrentDateInString().equals(textView.getText().toString()) ?
                Utils.getCurrentDateInMiliSecs() :
                Utils.getDateInMiliSecs(textView.getText().toString());
    }

    public static String removeTrailingZeros(String s) {
        return s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    public static String removeTrailingZeros(float amt) {
//        String s =
        return removeTrailingZeros(String.valueOf((float) Math.round(amt * 100) / 100));
//        return s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    public static boolean isDateGreater(Entry entry1, Entry entry2) {
        if (entry1.getEntryMonth() > entry2.getEntryMonth())
            return true;
        else if (entry1.getEntryYear() > entry2.getEntryYear())
            return true;
        else return false;
    }

    public static void uploadFile(final Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Upload Files");
        alertDialogBuilder
                .setMessage("Do you want to upload files ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        uploadFileLogic(context);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void downloadFile(final Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Download Files");
        alertDialogBuilder
                .setMessage("Do you want to download files ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        downloadFileLogic(context);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void uploadFileLogic(Context context) {
        try {
//            File sd = Environment.getExternalStorageDirectory();
            File sd = new File(Environment.getExternalStorageDirectory() + File.separator + "Wallet BackUp");
            sd.mkdirs();

            if (sd.canWrite()) {
                String backupDBPath = "/data/data/" + context.getPackageName() + "/databases/wallet_db";
                String currentDBPath = "wallet_db.db";
                File currentDB = new File(sd, currentDBPath);
                File backupDB = new File(backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                } else
                    Toast.makeText(context, "" + "Cannot find " + backupDBPath, Toast.LENGTH_SHORT).show();
            }
            if (sd.canWrite()) {
                String backupDBPath = "/data/data/" + context.getPackageName() + "/databases/wallet_db-shm";
                String currentDBPath = "wallet_db-shm";
                File currentDB = new File(sd, currentDBPath);
                File backupDB = new File(backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                } else
                    Toast.makeText(context, "" + "Cannot find " + backupDBPath, Toast.LENGTH_SHORT).show();
            }
            if (sd.canWrite()) {
                String backupDBPath = "/data/data/" + context.getPackageName() + "/databases/wallet_db-wal";
                String currentDBPath = "wallet_db-wal";
                File currentDB = new File(sd, currentDBPath);
                File backupDB = new File(backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                } else
                    Toast.makeText(context, "" + "Cannot find " + backupDBPath, Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context, "" + "Files uploaded", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, MainActivity.class));
        } catch (Exception e) {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void downloadFileLogic(Context context) {
        try {
//            File sd = Environment.getExternalStorageDirectory();
            File sd = new File(Environment.getExternalStorageDirectory() + File.separator + "Wallet BackUp");
            sd.mkdirs();

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + context.getPackageName() + "/databases/wallet_db";
                String backupDBPath = "wallet_db.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                } else
                    Toast.makeText(context, "" + "Cannot save " + backupDBPath, Toast.LENGTH_SHORT).show();
            }
            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + context.getPackageName() + "/databases/wallet_db-shm";
                String backupDBPath = "wallet_db-shm";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                } else
                    Toast.makeText(context, "" + "Cannot save " + backupDBPath, Toast.LENGTH_SHORT).show();
            }

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + context.getPackageName() + "/databases/wallet_db-wal";
                String backupDBPath = "wallet_db-wal";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                } else
                    Toast.makeText(context, "" + "Cannot save " + backupDBPath, Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context, "" + "Files saved", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
