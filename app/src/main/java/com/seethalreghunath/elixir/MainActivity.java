package com.seethalreghunath.elixir;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private TextView eheading;
    private Button eregister;
    private Button edash;
    private Button elogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eregister=(Button) findViewById(R.id.register_button);
        edash=(Button) findViewById(R.id.button2);
        elogin=(Button) findViewById(R.id.login);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent=new Intent(getApplicationContext(), Dashboard.class);
            finish();
            startActivity(intent);
        }


        eregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), RegistrationActivity.class);
                finish();
                startActivity(intent);
            }
        });

        elogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Login.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
