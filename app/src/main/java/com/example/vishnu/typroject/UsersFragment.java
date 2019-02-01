package com.example.vishnu.typroject;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {

    ListView listViewAll;
    DatabaseReference databaseUsers;
    List<UserAllInstantiation> userList;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tablayout_users, container, false);

        listViewAll = v.findViewById(R.id.listViewAll);
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        userList = new ArrayList<>();



        return v;
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

                AllUsersAdapter allUsersAdapter = new AllUsersAdapter(getActivity(),userList);
                listViewAll.setAdapter(allUsersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
