package com.rr.project.myapplication.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.rr.project.myapplication.TabActivity;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        DatePickerDialog dpDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        DatePicker datePicker = dpDialog.getDatePicker();
        datePicker.setMaxDate(c.getTimeInMillis());//set the current day as the max date

        return dpDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String stringMonth = String.valueOf(month);
        String stringDay= String.valueOf(dayOfMonth);

        if (month < 10)
            stringMonth = "0" + month;
        if (dayOfMonth < 10)
            stringDay = "0" + dayOfMonth;

        String date = stringDay + "/" + stringMonth + "/" + year;
        ((TabActivity) getActivity()).setDate(date);
    }
}
