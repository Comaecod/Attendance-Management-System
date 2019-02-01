package com.example.vishnu.typroject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimetableActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    TextView datefont;
    TextView lecturetimefont, practicaltimefont;
    Button setDate;
    Button lecture, practical,submitTimetable;
    Spinner course;
    Spinner tybvoc, sybvoc, fybvoc;
    ArrayAdapter adapter, adapter1, adapter2, adapter3;
    TextView courset, subject;
    DatabaseReference timetableData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        datefont = findViewById(R.id.datefont);
        lecturetimefont = findViewById(R.id.lecturetimefont);
        practicaltimefont = findViewById(R.id.practicaltimefont);
        setDate = findViewById(R.id.selectdate);
        lecture = findViewById(R.id.lecture);
        practical = findViewById(R.id.practical);
        courset = findViewById(R.id.course);
        subject = findViewById(R.id.subject);
        submitTimetable = findViewById(R.id.submitdata);


        course = findViewById(R.id.spinnercourse);
        adapter = ArrayAdapter.createFromResource(this, R.array.course, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        course.setAdapter(adapter);

        tybvoc = findViewById(R.id.spinnertybvoc);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.tybvoc, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        tybvoc.setAdapter(adapter1);

        sybvoc = findViewById(R.id.spinnersybvoc);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.sybvoc, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sybvoc.setAdapter(adapter2);

        fybvoc = findViewById(R.id.spinnerfybvoc);
        adapter3 = ArrayAdapter.createFromResource(this, R.array.fybvoc, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        fybvoc.setAdapter(adapter3);

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if (item.equals("TYBVoc - S.D.")) {
                    tybvoc.setVisibility(View.VISIBLE);
                    sybvoc.setVisibility(View.GONE);
                    fybvoc.setVisibility(View.GONE);
                    courset.setText("Course: " + item);
                    courset.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Select Subject For " + item, Toast.LENGTH_SHORT).show();
                } else if (item.equals("SYBVoc - S.D.")) {
                    tybvoc.setVisibility(View.GONE);
                    sybvoc.setVisibility(View.VISIBLE);
                    fybvoc.setVisibility(View.GONE);
                    subject.setVisibility(View.GONE);
                    courset.setText("Course: " + item);
                    courset.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Select Subject For " + item, Toast.LENGTH_SHORT).show();
                } else if (item.equals("FYBVoc - S.D.")) {
                    tybvoc.setVisibility(View.GONE);
                    sybvoc.setVisibility(View.GONE);
                    fybvoc.setVisibility(View.VISIBLE);
                    subject.setVisibility(View.GONE);
                    courset.setText("Course: " + item);
                    courset.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Select Subject For " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Select Your Course", Toast.LENGTH_SHORT).show();
            }
        });

//        if(tybvoc.getVisibility() == View.VISIBLE){
//            subject.setText("Subject: " + tybvoc.getSelectedItem().toString());
//            subject.setVisibility(View.VISIBLE);
//        }
//
//        else if(sybvoc.getVisibility() == View.VISIBLE){
//            subject.setText("Subject: " + sybvoc.getSelectedItem().toString());
//            subject.setVisibility(View.VISIBLE);
//        }
//
//        else if(fybvoc.getVisibility() == View.VISIBLE){
//            subject.setText("Subject: " + fybvoc.getSelectedItem().toString());
//            subject.setVisibility(View.VISIBLE);
//        }


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String calendarformat = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        datefont.setText(calendarformat);

//        Calendar time = Calendar.getInstance();
//        int hour = time.get(Calendar.HOUR_OF_DAY);
//        int minutes = time.get(Calendar.MINUTE);

        Typeface tf = Typeface.createFromAsset(getAssets(), "Vishnu.ttf");

        lecturetimefont.setTypeface(tf);
        practicaltimefont.setTypeface(tf);
        datefont.setTypeface(tf);
        courset.setTypeface(tf);
        subject.setTypeface(tf);

        tybvoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                    if (item.equals("Project")) {
                        subject.setVisibility(View.VISIBLE);
                        subject.setText("Subject: " + item);
                    } else if (item.equals("BigData")) {
                        subject.setVisibility(View.VISIBLE);
                        subject.setText("Subject: " + item);
                    } else if (item.equals("Python")) {
                        subject.setVisibility(View.VISIBLE);
                        subject.setText("Subject: " + item);
                    } else if (item.equals("JQuery/PHP")) {
                        subject.setVisibility(View.VISIBLE);
                        subject.setText("Subject: " + item);
                    } else if (item.equals("Entrepreneurship")) {
                        subject.setVisibility(View.VISIBLE);
                        subject.setText("Subject: " + item);
                    } else if (item.equals("Strategic Management")) {
                        subject.setVisibility(View.VISIBLE);
                        subject.setText("Subject: " + item);
                    } else if (item.equals("Managerial Economics")) {
                        subject.setVisibility(View.VISIBLE);
                        subject.setText("Subject: " + item);
                    }  else if (item.equals("MultiMedia")) {
                        subject.setVisibility(View.VISIBLE);
                        subject.setText("Subject: " + item);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Select Your Subject", Toast.LENGTH_SHORT).show();
            }
        });

        sybvoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                if (item.equals("Advanced Java")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("Computer Graphics")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("Software Testing")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("JQuery/PHP")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("Principles Of Management")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("Business Communications")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("Public Relations")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                }  else if (item.equals("Accountancy & Book Keeping")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Select Your Subject", Toast.LENGTH_SHORT).show();
            }
        });

        fybvoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                if (item.equals("Software Engineering")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("Discrete Mathematics")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("Web Designing")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("O.O.P.S.")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("Business Communications")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("MicroSoft Office")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                } else if (item.equals("French")) {
                    subject.setVisibility(View.VISIBLE);
                    subject.setText("Subject: " + item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Select Your Subject", Toast.LENGTH_SHORT).show();
            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        lecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                practicaltimefont.setVisibility(View.GONE);
                lecturetimefont.setVisibility(View.VISIBLE);
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker 1");
            }
        });

        practical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lecturetimefont.setVisibility(View.GONE);
                practicaltimefont.setVisibility(View.VISIBLE);
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker 2");
            }
        });

        timetableData = FirebaseDatabase.getInstance().getReference("Timetable");

        submitTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = timetableData.push().getKey();
                Timetable timetable = new Timetable(datefont.getText().toString(),lecturetimefont.getText().toString(),practicaltimefont.getText().toString(),courset.getText().toString(),subject.getText().toString());
                timetableData.child(id).setValue(timetable);

                startActivity(new Intent(getApplicationContext(),AttendanceMainActivity.class));
            }
        });
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

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        if (lecturetimefont.getText().toString().contains("Lecture")) {

            String amPm1;
            String amPm2;
            if (i >= 12) {
                amPm1 = "PM";
            } else {
                amPm1 = "AM";
            }

            int x = i;
            int y = i1 + 50;

            if (y > 59) {
                x++;
                y = y - 60;
            }
            if (x > 23) {
                x = 0;
            }
            if (x >= 12) {
                amPm2 = "PM";
            } else {
                amPm2 = "AM";
            }

            lecturetimefont.setText("Lecture: " + String.format("%02d:%02d", i, i1) + " " + amPm1 + " to " + String.format("%02d:%02d", x, y) + " " + amPm2);
        }
        if (practicaltimefont.getText().toString().contains("Practical")) {

            String amPm1;
            String amPm2;
            if (i >= 12) {
                amPm1 = "PM";
            } else {
                amPm1 = "AM";
            }

            int x = i + 2;
            int y = i1 + 30;

            if (y > 59) {
                x++;
                y = y - 60;
            }
            if (x > 23) {
                x = 0;
            }
            if (x >= 12) {
                amPm2 = "PM";
            } else {
                amPm2 = "AM";
            }

            practicaltimefont.setText("Practical: " + String.format("%02d:%02d", i, i1) + " " + amPm1 + " to " + String.format("%02d:%02d", x, y) + " " + amPm2);

        }
    }
}
