package com.example.vishnu.typroject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;


public class MobileVerificationActivity extends AppCompatActivity {

    private EditText phoneNumber;
    private PinView verCode;
    private TextView resendCode;
    private Button sendVerificationCode;
    private Button verifyCode;
    CountryCodePicker ccp;
    String number;
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationStateChangedCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        phoneNumber = findViewById(R.id.mobilenumber);
        verCode = findViewById(R.id.pinView);
        resendCode = findViewById(R.id.resendcode);
        sendVerificationCode = findViewById(R.id.sendverificationcode);
        verifyCode = findViewById(R.id.verifycode);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phoneNumber);
        verifyCode.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();

        sendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode();
            }
        });

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resendCodeMethod();
            }
        });

        verifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Please wait!", Toast.LENGTH_SHORT).show();
                verifyCodeSent();
            }
        });
    }

    private void sendCode() {

        number = ccp.getFullNumberWithPlus();

        if (number.isEmpty() || number.length() < 10) {
            phoneNumber.setError("Invalid Number!");
            phoneNumber.requestFocus();
            return;
        }


        if (isInternetConnection()) {
            findViewById(R.id.verificationcode).setVisibility(View.VISIBLE);
            verCode.setVisibility(View.VISIBLE);
            verifyCode.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);
            ccp.setVisibility(View.GONE);
            phoneNumber.setVisibility(View.GONE);
            sendVerificationCode.setVisibility(View.GONE);
            findViewById(R.id.viewmobver).setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "An OTP has been sent to " + number, Toast.LENGTH_SHORT).show();
            setUpVerificationCallbacks();

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    number,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    verificationStateChangedCallbacks
            );
        } else {
            Toast.makeText(getApplicationContext(), "Internet is unavailable at the moment!", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean isInternetConnection() {

        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else
            connected = false;
        return connected;
    }

    private void setUpVerificationCallbacks() {


        verificationStateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String codeFromSMS = phoneAuthCredential.getSmsCode();
                verCode.setText(codeFromSMS);
                resendCode.setEnabled(true);
                verifyCode.setEnabled(true);
                verCode.setText(codeFromSMS);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(MobileVerificationActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                phoneVerificationId = s;
                resendingToken = forceResendingToken;
                verifyCode.setEnabled(true);
                resendCode.setEnabled(true);
            }
        };
    }

    private void verifyCodeSent() {
        String code = verCode.getText().toString().trim();

        if (code.isEmpty() || code.length() < 6) {
            verCode.setError("Invalid Code!");
            verCode.requestFocus();
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendCodeMethod() {
        number = ccp.getFullNumberWithPlus();
        if (number.isEmpty() || number.length() < 10) {
            phoneNumber.setError("Invalid Number!");
            phoneNumber.requestFocus();
            return;
        }

        if (isInternetConnection()) {
            Toast.makeText(getApplicationContext(), "New OTP has been sent to " + ccp.getFullNumberWithPlus(), Toast.LENGTH_SHORT).show();
            setUpVerificationCallbacks();

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    number,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    verificationStateChangedCallbacks
            );
        } else {
            Toast.makeText(getApplicationContext(), "Internet is unavailable at the moment!", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {

        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    resendCode.setEnabled(false);
                    verifyCode.setEnabled(false);

                    Toast.makeText(getApplicationContext(), "Verification Successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MobileVerificationActivity.this, MobileProfileActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(MobileVerificationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MobileVerificationActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}