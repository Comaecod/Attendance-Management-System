package com.example.vishnu.typroject;


import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimetableFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    TextView datefont,timefont;
    Button setDate;
    Button setTime;

    public TimetableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tablayout_timetable, container, false);
        datefont = v.findViewById(R.id.datefont);
        timefont = v.findViewById(R.id.timefont);
        setDate = v.findViewById(R.id.selectdate);
        setTime = v.findViewById(R.id.selecttime);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getActivity().getSupportFragmentManager(),"Date Picker");
            }
        });

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"Vishnu.ttf");

        timefont.setTypeface(tf);
        datefont.setTypeface(tf);

        return v;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);

        String calendarformat = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        datefont.setText(calendarformat);
    }
}
