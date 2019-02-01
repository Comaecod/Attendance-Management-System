package com.example.vishnu.typroject;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment {

    TextView attendance, leave, subject, course, blacklisted, attendLectures;

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_tablayout_attendance, container, false);
        attendance = v.findViewById(R.id.attendancefont);
        leave = v.findViewById(R.id.leavefont);
        subject = v.findViewById(R.id.subjectfont);
        course = v.findViewById(R.id.coursefont);
        blacklisted = v.findViewById(R.id.blacklistedfont);
        attendLectures = v.findViewById(R.id.pleaseattendfont);


        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"Vishnu.ttf");

        attendance.setTypeface(tf);
        leave.setTypeface(tf);
        subject.setTypeface(tf);
        course.setTypeface(tf);
        blacklisted.setTypeface(tf);
        attendLectures.setTypeface(tf);

        return v;



//        return inflater.inflate(R.layout.fragment_tablayout_attendance, container, false);
    }

}
