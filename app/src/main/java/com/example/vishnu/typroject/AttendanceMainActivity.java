package com.example.vishnu.typroject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AttendanceMainActivity extends AppCompatActivity {

    String[] names = {"Vishnu Vardhan", "Sakshi Sangtani", "Anuj Singh", "Abdullah Syed", "Saloni Paleja", "Hina Raiba", "Ashok Sethiya", "Jayesh Bablani", "Tina Mehta", "Jayesh Badala"};
    ListView listView;
    Button submitToDatabase;
    DatabaseReference attendance_data;
    ArrayList a1, a2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_main);

        listView = findViewById(R.id.attendancesubmitlistview);

        submitToDatabase = findViewById(R.id.submitdatabase);

        attendance_data = FirebaseDatabase.getInstance().getReference("AttendanceReal");

        a1 = new ArrayList();
        a2 = new ArrayList();

        ListAdapter adapter = new AttendanceAdapter(this, names);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = String.valueOf(adapterView.getItemAtPosition(i));
                Toast.makeText(AttendanceMainActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        });

        submitToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i <= listView.getLastVisiblePosition(); i++) {

                    String nameS = String.valueOf(listView.getItemAtPosition(i));
                    String idS = attendance_data.push().getKey();

                    AttendanceData data = new AttendanceData(idS, nameS);

                    attendance_data.child(idS).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), task.getException().toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

//                    a1.add(nameS);
//                    a2.add(idS);



                }
                Toast.makeText(getApplicationContext(),"Data Entered Successfully!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),IndexActivity.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
