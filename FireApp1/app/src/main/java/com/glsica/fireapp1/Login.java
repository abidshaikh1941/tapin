package com.glsica.fireapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private TextView error;
    private EditText mphoneNumber;
    private ProgressBar progressBar;
    private Button genButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mphoneNumber = (EditText)findViewById(R.id.phone_number);
        error = (TextView)findViewById(R.id.login_error);
        progressBar = (ProgressBar)findViewById(R.id.login_progress_bar);
        genButton = (Button)findViewById(R.id.login_button);


        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone_number = mphoneNumber.getText().toString();

                if (phone_number.isEmpty()) {
                    /*error.setText("Please Enter Number");
                    error.setVisibility(View.VISIBLE);*/
                    mphoneNumber.setError("Enter Phone Number");
                }
                else if (phone_number.length()!=10)
                {
                        /*error.setText("Number is invalid");
                        error.setVisibility(View.VISIBLE);*/
                    mphoneNumber.setError("Enter Valid Number");
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    genButton.setEnabled(false);
                    phone_number="+91"+mphoneNumber.getText().toString();

                    error.setText("Sending Otp...");
                    error.setVisibility(View.VISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(phone_number,60, TimeUnit.SECONDS,Login.this,mCallbacks);
                }

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                error.setText("Verification Failed");
                error.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                genButton.setEnabled(true);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Intent otpIntent = new Intent(Login.this, Otp.class);
                otpIntent.putExtra("AuthCredential",s);
                otpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                otpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(otpIntent);
                finish();
                /* new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                    }
                },10000);*/

            }
        };

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            sendUserToHome();
                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                error.setText("Enter Valid Otp");
                                error.setVisibility(View.VISIBLE);
                            }
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        genButton.setEnabled(true);

                    }
                });
    }

    public void sendUserToHome()
    {
        Intent homeIntent = new Intent(this,MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser!=null)
        {
            Intent homeIntent = new Intent(this,MainActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeIntent);
            finish();
        }

    }
}
