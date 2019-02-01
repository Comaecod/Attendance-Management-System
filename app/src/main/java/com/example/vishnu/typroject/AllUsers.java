package com.example.vishnu.typroject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllUsers extends AppCompatActivity {

    ListView listViewAll;
    DatabaseReference databaseUsers;
    List<UserAllInstantiation> userList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        listViewAll = findViewById(R.id.listViewAll);
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        userList = new ArrayList<>();



    }

    @Override
    public void onStart() {
        super.onStart();

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userList.clear();

                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    UserAllInstantiation user = userSnapshot.getValue(UserAllInstantiation.class);

                    userList.add(user);
                }

                AllUsersAdapter allUsersAdapter = new AllUsersAdapter(AllUsers.this,userList);
                listViewAll.setAdapter(allUsersAdapter);

//                listViewAll.setOnItemClickListener(new AdapterView.OnItemClickListener()
//                {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view,
//                                            int position, long id) {
//                        // TODO Auto-generated method stub
//
//                        // Getting listview click value into String variable.
//                        String TempListViewClickedValue = listValue[position].toString();
//
//                        Intent intent = new Intent(MainActivity.this, .class);
//
//                        // Sending value to another activity using intent.
//                        intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
//
//                        startActivity(intent);
//
//                    }
//                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
