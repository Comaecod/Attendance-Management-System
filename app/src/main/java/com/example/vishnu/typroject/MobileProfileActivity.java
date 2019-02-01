package com.example.vishnu.typroject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MobileProfileActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    //ImageView profilePic;
    CircleImageView profilePic;
    EditText nameAlias;
    Uri uriProfile;
    ProgressBar progressBar1, progressBar2;
    String profileImageUrl;
    FirebaseAuth mAuth;
    TextView verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_profile);

        profilePic = findViewById(R.id.profilepic);
        nameAlias = findViewById(R.id.displayname);
        progressBar1 = findViewById(R.id.progressprofilepic);
        progressBar2 = findViewById(R.id.progressdisplayname);
        verify = findViewById(R.id.verifieduser);
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadUserInfo();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayImageChooser();
            }
        });

        findViewById(R.id.saveprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });

        findViewById(R.id.nextPhoneReg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user.getPhotoUrl() != null) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), IndexActivity.class));
                } else
                    Toast.makeText(getApplicationContext(), "Please select a profile picture!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    private void loadUserInfo() {

        final FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            if(user.getPhotoUrl() != null){
                Picasso.with(this).load(user.getPhotoUrl()).placeholder(R.drawable.man_profile_image).noFade().into(profilePic);
            }
            if(user.getDisplayName() != null){
                nameAlias.setText(user.getDisplayName());
            }
        }
    }

    private void saveUserInfo() {
        String nameDisplay = nameAlias.getText().toString().trim();

        if(nameDisplay.isEmpty()){
            nameAlias.setError("Alias Name Is Required!");
            nameAlias.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null && profileImageUrl != null){
            progressBar2.setVisibility(View.VISIBLE);
            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nameDisplay)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(request)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar2.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Profile Is Updated!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,MainActivity.class));
                break;
//            case R.id.qrCodeScan:
//                startActivity(new Intent(this,ScanQRCode.class));
//                break;
//            case R.id.qrCodeGen:
//                startActivity(new Intent(this,GenerateQRCode.class));
//                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriProfile = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfile);
                profilePic.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        StorageReference profileImgRef = FirebaseStorage.getInstance().getReference("vishnuProfileDatabase/" + System.currentTimeMillis() + ".jpg");
        if(uriProfile != null){
            progressBar1.setVisibility(View.VISIBLE);
            profileImgRef.putFile(uriProfile)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar1.setVisibility(View.GONE);
                            profileImageUrl = taskSnapshot.getStorage().getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar1.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void displayImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Your Profile Image"), CHOOSE_IMAGE);
    }

    @Override
    public void onBackPressed() {
        new MaterialStyledDialog.Builder(this)
                .setTitle("Exit!")
                .setDescription("Are You Sure?")
                .setCancelable(false)
                .setIcon(R.drawable.ams_red_2)
                .setHeaderDrawable(R.drawable.dialog_background)
                .setStyle(Style.HEADER_WITH_ICON)
                .withDialogAnimation(true)
                .setPositiveText("Yes")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .setNegativeText("No")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.cancel();
                    }
                })
                .setNeutralText("Cancel")
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
