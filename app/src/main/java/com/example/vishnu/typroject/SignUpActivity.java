package com.example.vishnu.typroject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;
import com.xwray.passwordview.PasswordView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameSignup, fullNameSignup, mobNoSignup, keySignup;
    PasswordView passwordSignup;
    CountryCodePicker ccp;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    Spinner att1;
    //ArrayAdapter<String> adapter;
    ArrayAdapter<CharSequence> adapter1;
    Spinner att2;
    ArrayAdapter<CharSequence> adapter2;
//    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        usernameSignup = findViewById(R.id.usernamesignup);
        passwordSignup = findViewById(R.id.passwordsignup);
        keySignup = findViewById(R.id.keysignup);
        fullNameSignup = findViewById(R.id.nameOfUser);
        mobNoSignup = findViewById(R.id.mobilePhoneNumber);
        progressBar = findViewById(R.id.progresssignup);
        ccp = findViewById(R.id.ccp);

        //keySignup.setEnabled(false);

//        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");


        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.loginsignup).setOnClickListener(this);

        //String [] values = {"Time at Residence","Under 6 months","6-12 months","1-2 years","2-4 years","4-8 years","8-15 years","Over 15 years",};
        att1 = findViewById(R.id.spinneratt1);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        //adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,values);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        att1.setAdapter(adapter1);


        att2 = findViewById(R.id.spinneratt2);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.course, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        att2.setAdapter(adapter2);

        att1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if (item.equals("HOD")) {
                    keySignup.setVisibility(View.VISIBLE);
                    att2.setVisibility(View.GONE);
                    findViewById(R.id.viewspinner).setVisibility(View.VISIBLE);
                    keySignup.requestFocus();
                    Toast.makeText(getApplicationContext(), "Enter Level Clearance Key For " + item, Toast.LENGTH_SHORT).show();
                } else if (item.equals("Faculty")) {
                    keySignup.setVisibility(View.VISIBLE);
                    att2.setVisibility(View.GONE);
                    findViewById(R.id.viewspinner).setVisibility(View.VISIBLE);
                    keySignup.requestFocus();
                    Toast.makeText(getApplicationContext(), "Enter Level Clearance Key For " + item, Toast.LENGTH_SHORT).show();
                } else if (item.equals("Student")) {
                    keySignup.setVisibility(View.GONE);
                    att2.setVisibility(View.VISIBLE);
                    findViewById(R.id.viewspinner).setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please Select Your Course", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginsignup:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.signup:
                registerUser();
                break;
        }
    }

    private void registerUser() {

        final String username = usernameSignup.getText().toString().trim();
        final String password = passwordSignup.getText().toString().trim();
        final String fullNameUser = fullNameSignup.getText().toString().trim();
        final String phoneNumber = mobNoSignup.getText().toString().trim();
        final String key = keySignup.getText().toString().trim();
        final String att1s = att1.getSelectedItem().toString();
        final String att2s = att2.getSelectedItem().toString();

        if (fullNameUser.isEmpty()) {
            fullNameSignup.setError("Name Is Required!");
            fullNameSignup.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            usernameSignup.setError("Username Is Required!");
            usernameSignup.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            usernameSignup.setError("Username Must Be A Valid Email Id!");
            usernameSignup.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password is required!", Toast.LENGTH_SHORT).show();
            passwordSignup.requestFocus();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password must be atleast six characters!", Toast.LENGTH_SHORT).show();
            passwordSignup.requestFocus();
            return;
        }

        if (phoneNumber.isEmpty() || phoneNumber.length() < 10) {
            mobNoSignup.setError("Invalid Mobile Number");
            mobNoSignup.requestFocus();
            return;
        }

        if(att1s.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please select your role",Toast.LENGTH_SHORT).show();
            return;
        }

        if (att1s.equals("HOD")) {
            if (!key.equals("HOD123")) {
                keySignup.setError("Invalid Key!");
                keySignup.requestFocus();
                return;
            }
        }

        if (att1s.equals("Faculty")) {
            if (!key.equals("FAC123")) {
                keySignup.setError("Invalid Key!");
                keySignup.requestFocus();
                return;
            }
        }

        if (att1s.equals("Student") && att2s.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Select A Course", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    final User user = new User(
                            username,
                            password,
                            fullNameUser,
                            ccp.getSelectedCountryCodeWithPlus() + phoneNumber,
                            att1s,
                            att2s
                    );

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                if (att2.getVisibility() == View.VISIBLE) {
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Student Registration Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                }
                                else if(keySignup.getVisibility() == View.VISIBLE){
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Administrator Registration Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), ProfileActivityAdmin.class));
                                }
//                                Intent i = new Intent(getApplicationContext(),MobileVerificationSignup.class);
//                                i.putExtra("fullPhoneNumber",ccp.getDefaultCountryCodeWithPlus()+ phoneNumber);
//                                startActivity(i);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

//                    String id = databaseUsers.push().getKey();
//                    UserAllInstantiation instantiation = new UserAllInstantiation(id,username,password,fullNameUser,ccp.getDefaultCountryCodeWithPlus() + phoneNumber,att1s,att2s);
//                    databaseUsers.child(id).setValue(instantiation).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()) {
//                                Toast.makeText(getApplicationContext(),"User Data Entered!",Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    public String codes(String key) {
//        if (key.equalsIgnoreCase("VISHNU1998")) {
//            return "Admin";
//        } else {
//            return "User";
//        }
//    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
}
