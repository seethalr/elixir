package com.seethalreghunath.elixir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {


    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String PHONENUMBER = "phonenumber";
    public static final String USERNAME="username";
    public static final String PASSWORD="password";
    public static final String LOGIN="login";
    public static final String IVALUE= "ivalue";
    public static final String JVALUE= "jvalue";
    public static final String AUSER= "admin";
    public static final String APASS= "adminelixir";
    public static final String APHONE= "9400736899";


    private EditText eusername;
    private EditText epassword;
    private EditText ephonenum;
    private Button register_button;
    private FirebaseAuth eauth;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        eusername=(EditText) findViewById(R.id.username);
        epassword=(EditText) findViewById(R.id.password);
        ephonenum=(EditText) findViewById(R.id.phonenum);
        back=(Button) findViewById(R.id.back);
        register_button=(Button) findViewById(R.id.registernow_button);
        eauth=FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistrationActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset errors displayed in the form.
                eusername.setError(null);
                epassword.setError(null);

                // Store values at the time of the login attempt.
                String username = eusername.getText().toString();
                String password = epassword.getText().toString();
                String phonenumber=ephonenum.getText().toString();

                boolean cancel = false;
                View focusView = null;


                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
                    epassword.setError(getString(R.string.error_invalid_password));
                    focusView = epassword;
                    cancel = true;
                }

                // Check for a valid email address.
                if (TextUtils.isEmpty(username)) {
                    eusername.setError(getString(R.string.error_field_required));
                    focusView = eusername;
                    cancel = true;
                }

                if(TextUtils.isEmpty(phonenumber)){
                    ephonenum.setError("This Field is required");
                    focusView=ephonenum;
                    cancel=true;
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                    Toast.makeText(RegistrationActivity.this, "Attempt Failed", Toast.LENGTH_SHORT).show();
                } else {
                    if(username.equals("admin") && password.equals("adminelixir") && phonenumber.equals("9400736899"))
                    {
                        Intent intent=new Intent(RegistrationActivity.this, Database.class);
                        finish();
                        startActivity(intent);
                    }
                    savedetails();
                    Intent intent=new Intent(RegistrationActivity.this, Verify.class);
                    finish();
                    startActivity(intent);
                }
            }
        });


    }

    private void savedetails(){
        String phno=ephonenum.getText().toString();
        String uname=eusername.getText().toString();
        String pass=epassword.getText().toString();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PHONENUMBER,phno);
        editor.putString(USERNAME,uname);
        editor.putString(PASSWORD,pass);
        editor.putInt(IVALUE, 0 );
        editor.putInt(JVALUE, 0 );
        editor.apply();
    }



    private boolean isPasswordValid(String password) {
        return password.length()>6;
    }


    }
