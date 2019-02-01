package com.example.vishnu.typroject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 101;
    Button login;
    Button signup;
    Button loginMobile;
    GoogleSignInButton googleSignInButton;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        loginMobile = (Button) findViewById(R.id.loginmobile);
        googleSignInButton = (GoogleSignInButton) findViewById(R.id.google);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

        loginMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MobileVerificationActivity.class);
                startActivity(i);
                finish();
            }
        });

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                            Toast.makeText(getApplicationContext(), "User LoginActivity Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(),IndexActivity.class));
        }
    }

//    @Override
//    public void onBackPressed() {
//        final Dialog d = new Dialog(MainActivity.this);
//
//        d.setContentView(R.layout.custom_alert_dialog);
//
//        d.show();
//        d.setCancelable(false);
//
//        Button yes = d.findViewById(R.id.yes);
//        Button no = d.findViewById(R.id.no);
//        Button cancel = d.findViewById(R.id.cancel);
//
//        yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.exit(0);
//            }
//        });
//
//        no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                d.dismiss();
//            }
//        });
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                d.dismiss();
//            }
//        });
//    }


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
        //super.onBackPressed();
    }
}
