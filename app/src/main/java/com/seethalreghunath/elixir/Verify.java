package com.seethalreghunath.elixir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class Verify extends AppCompatActivity {

    public static final String PHONENUMBER = "phonenumber";
    public static final String USERNAME = "username";
    public static final String PASSWORD="password";

    private EditText eotp;
    private Button esend_button;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String phonenum;
    private String name;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
    private int buttontype=0;
    private Button eback;
    private Button resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify);

        eotp = (EditText) findViewById(R.id.otp);
        esend_button = (Button) findViewById(R.id.sendotp_button);
        eback=(Button) findViewById(R.id.back);
        mAuth = FirebaseAuth.getInstance();
        resend=(Button) findViewById(R.id.resend);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        name = preferences.getString(USERNAME, "");
        phonenum = preferences.getString(PHONENUMBER, "");
        phonenum = "++91" + phonenum;

        eback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Verify.this, RegistrationActivity.class);
                finish();
                startActivity(intent);
            }
        });
        esend_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttontype == 0) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phonenum,
                            60,
                            TimeUnit.SECONDS,
                            Verify.this,
                            mCallbacks
                    );
                }
                else{
                    String verificationcode=eotp.getText().toString();
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId, verificationcode);
                    signInWithPhoneAuthCredential(credential);

                }
            }


        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String verificationcode=eotp.getText().toString();
                    signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Verify.this, "There was an error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                buttontype=1;
                esend_button.setText("Verify OTP");
                resend.setVisibility(View.VISIBLE);

                // ...
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
                            FirebaseUser user = task.getResult().getUser();
                            Intent intent=new Intent(Verify.this, Profile.class);
                            startActivity(intent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(Verify.this, "There was an error!", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}