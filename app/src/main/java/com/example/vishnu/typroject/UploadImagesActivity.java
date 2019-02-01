package com.example.vishnu.typroject;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class UploadImagesActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUploadImage;
    private TextView mTextViewShowUploads;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private EditText mEditTextFileName;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask storageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);

        mButtonChooseImage = findViewById(R.id.choosefileupload);
        mButtonUploadImage = findViewById(R.id.fileuploadfirebase);
        mTextViewShowUploads = findViewById(R.id.showuploads);
        mImageView = findViewById(R.id.chosenimage);
        mProgressBar = findViewById(R.id.progressHorizon);
        mEditTextFileName = findViewById(R.id.fileName);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads/");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        mButtonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (storageTask != null && storageTask.isInProgress())
                    Toast.makeText(getApplicationContext(), "Upload In Progress! Wait!", Toast.LENGTH_SHORT).show();
                else
                    uploadFile();

            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagesActivity();
            }
        });
    }

    private void openImagesActivity() {

        Intent intent = new Intent(this,UploadedImagesActivity.class);
        startActivity(intent);
    }

    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadFile() {

        final String fileName = mEditTextFileName.getText().toString().trim();

        if (fileName.isEmpty()) {
            mEditTextFileName.setError("File Name Required!");
            mEditTextFileName.requestFocus();
            return;
        }

        if (mImageUri != null) {
            StorageReference storageReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            storageTask = storageReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 1500);

                            mButtonUploadImage.setText("Upload File");

                            Toast.makeText(getApplicationContext(), "File Uploaded Successfully!", Toast.LENGTH_SHORT).show();

                            UploadImages uploadImages = new UploadImages(fileName, taskSnapshot.getStorage().getDownloadUrl().toString());

                            String uploadID = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadID).setValue(uploadImages);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                            mButtonUploadImage.setText((int) progress + "%");

                        }
                    });

        } else {
            Toast.makeText(getApplicationContext(), "Please Choose An Image!", Toast.LENGTH_SHORT).show();
        }


    }

    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            mImageUri = data.getData();

            Picasso
                    .with(this)
                    .load(mImageUri)
                    .error(R.drawable.no_image_selected)
                    .placeholder(R.drawable.image_loading)
                    .into(mImageView);
        }
    }
}
