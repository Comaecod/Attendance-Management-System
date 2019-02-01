package com.example.vishnu.typroject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.xwray.passwordview.PasswordView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText usernameLogin;
    PasswordView passwordLogin;
    ProgressBar progressBar;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.signuplogin).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        usernameLogin = findViewById(R.id.usernamelogin);
        passwordLogin = findViewById(R.id.passwordlogin);

        progressBar = findViewById(R.id.progresslogin);

        forgotPassword = findViewById(R.id.forgotpasswordlogin);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signuplogin:
                finish();
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.login:
                loginUser();
                break;
        }
    }

    private void loginUser() {

        String username = usernameLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if (username.isEmpty()) {
            usernameLogin.setError("Username Is Required!");
            usernameLogin.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            usernameLogin.setError("Username Must Be A Valid Email Id!");
            usernameLogin.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Password is required!",Toast.LENGTH_SHORT).show();
            passwordLogin.requestFocus();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(),"Password must be atleast six characters!",Toast.LENGTH_SHORT).show();
            passwordLogin.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    Toast.makeText(getApplicationContext(), "User LoginActivity Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
