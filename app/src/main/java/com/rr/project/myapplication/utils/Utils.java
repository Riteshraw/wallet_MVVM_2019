package com.rr.project.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.rr.project.myapplication.dao.Entry;

import java.math.BigDecimal;
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
        return String.valueOf((float) Math.round(amt * 100) / 100);
//        return s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    public static boolean isDateGreater(Entry entry1, Entry entry2) {
        if (entry1.getEntryMonth() > entry2.getEntryMonth())
            return true;
        else if (entry1.getEntryYear() > entry2.getEntryYear())
            return true;
        else return false;
    }

}
