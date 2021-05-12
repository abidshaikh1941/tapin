package com.glsica.fireapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class Otp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private EditText mOtp;
    private Button verifyBtn;
    private ProgressBar otpProgressBar;
    private TextView otpError;
    private String mAuthVerificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mAuthVerificationId = getIntent().getStringExtra("AuthCredential");

        mOtp = (EditText)findViewById(R.id.editTextOtp);
        otpProgressBar = (ProgressBar)findViewById(R.id.otp_progressbar);
        otpError = (TextView)findViewById(R.id.otp_error);
        verifyBtn=(Button)findViewById(R.id.verify_otp_Button);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp;
                otp = mOtp.getText().toString();
                if(otp.isEmpty())
                {
                    /*otpError.setText("Please Enter Otp");
                    otpError.setVisibility(View.VISIBLE);*/
                    mOtp.setError("Please Enter Otp");
                }
                else
                {
                    otpProgressBar.setVisibility(View.VISIBLE);
                    verifyBtn.setEnabled(false);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, otp);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //sendUserToHome();
                            sendUserToDetails();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                /*otpError.setText("Enter Valid Otp");
                                otpError.setVisibility(View.VISIBLE);*/
                                mOtp.setError("Enter Valid Otp");
                            }
                        }
                        otpProgressBar.setVisibility(View.INVISIBLE);
                        verifyBtn.setEnabled(true);

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser!=null)
        {
            sendUserToHome();
        }

    }
    public void sendUserToHome()
    {
        Intent homeIntent = new Intent(this,MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
    public void sendUserToDetails()
    {
        Intent detailsIntent = new Intent(this,Details.class);
        detailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        detailsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(detailsIntent);
        finish();
    }
}
