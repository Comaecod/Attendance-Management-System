package com.example.vishnu.typroject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText emailReset;
    Button reset;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailReset = findViewById(R.id.emailresetpassword);
        reset = findViewById(R.id.resetpassword);
        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressresetpassword);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailReset.getText().toString().trim();
                if(email.isEmpty()){
                    //Toast.makeText(getApplicationContext(),"Please Enter Your Email Id",Toast.LENGTH_SHORT).show();
                    emailReset.setError("Please Enter Your Email Id");
                    emailReset.requestFocus();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Reset Password link has been sent to your Email",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            }
                            else {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
