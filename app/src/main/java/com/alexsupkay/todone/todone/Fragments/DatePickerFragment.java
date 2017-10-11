package com.alexsupkay.todone.todone.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import com.alexsupkay.todone.todone.AddTaskActivity;
import com.alexsupkay.todone.todone.MainActivity;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        //If this is a datepicker inside of new party translate from the left textView
        if(getActivity() instanceof AddTaskActivity) {
            if( !(( (AddTaskActivity) getActivity()).getDateText().matches(""))) {
                String[] result = ((AddTaskActivity) getActivity()).getDateText().split(" |, ");
                year = Integer.parseInt(result[2]);
                try {
                    Date date = new SimpleDateFormat("MMMM", Locale.getDefault()).parse(result[0]);
                    c.setTime(date);
                } catch (ParseException e) {
                    Log.e("ERROR", e.toString());
                }
                month = c.get(Calendar.MONTH);
                day = Integer.parseInt(result[1]);
            }
        }

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    //When they set the date do this
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //If this is an instance of new party set left
        if(getActivity() instanceof  AddTaskActivity) {
            ((AddTaskActivity) getActivity()).setDateText(formatDate(year, month, day));
        }
    }

    private String formatDate(int year, int month, int day){
        return DateFormatSymbols.getInstance().getMonths()[month] + ", " + Integer.toString(day) + ", " + Integer.toString(year);
    }
}