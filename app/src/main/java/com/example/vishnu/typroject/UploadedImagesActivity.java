package com.example.vishnu.typroject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UploadedImagesActivity extends AppCompatActivity implements ImageAdapter.onItemClickListener{

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private DatabaseReference databaseReference;
    private FirebaseStorage mStorage;
    private List<UploadImages> uploadImages;
    private ProgressBar progressBar;
    private ValueEventListener mDBListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_images);

        progressBar = findViewById(R.id.progressBar_view);

        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uploadImages = new ArrayList<>();

        imageAdapter = new ImageAdapter(getApplicationContext(), uploadImages);
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(UploadedImagesActivity.this);

        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        mStorage = FirebaseStorage.getInstance();

        mDBListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                uploadImages.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UploadImages images = snapshot.getValue(UploadImages.class);
                    images.setmKey(snapshot.getKey());
                    uploadImages.add(images);
                }

                imageAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getApplicationContext(),"You clicked on position:" + position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Toast.makeText(getApplicationContext(),"You clicked on delete:" + position,Toast.LENGTH_SHORT).show();
//        UploadImages selectedItem = uploadImages.get(position);
//        final String selectedKey = selectedItem.getmKey();
//
//        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getmImageUrl());
//        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                databaseReference.child(selectedKey).removeValue();
//                Toast.makeText(UploadedImagesActivity.this, "Item Deleted Successfully!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mDBListener);
    }
}
