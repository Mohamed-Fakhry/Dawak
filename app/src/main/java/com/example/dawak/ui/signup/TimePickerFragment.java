package com.example.dawak.ui.signup;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Mohamed Fakhry on 24/02/2018.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    TimeCallback timeCallback;

    public TimePickerFragment() {}

    public static TimePickerFragment newInstance(int title) {
        TimePickerFragment frag = new TimePickerFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void setTimeCallback(TimeCallback timeCallback) {
        this.timeCallback = timeCallback;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        boolean isAm = true;
        if (hourOfDay > 11) {
            isAm = false;
            hourOfDay -= 12;
        }
        timeCallback.time(hourOfDay, minute, isAm);
    }

    public interface TimeCallback {
        void time(int hours, int minute, boolean isAm);
    }
}
