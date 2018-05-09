package com.seethalreghunath.elixir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    public static final String LOGIN="login";

    private Button emed_course;
    private Button eapp_feedback;
    private Button emed_app;
    private Button emenstrual_tracker;
    private Button eedit_profile;
    private Button elogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash);

        emed_app=(Button) findViewById(R.id.med_app);
        eapp_feedback=(Button) findViewById(R.id.app_feedback);
        emed_course=(Button) findViewById(R.id.medication_course);
        emenstrual_tracker=(Button) findViewById(R.id.menstrual_tracker);
        eedit_profile=(Button) findViewById(R.id.edit_profile);
        elogout=(Button) findViewById(R.id.logout);

        emed_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, Med_Appointment.class);
                finish();
                startActivity(intent);
            }
        });
        emed_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, MedicationCourse.class);
                finish();
                startActivity(intent);
            }
        });

        eapp_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, AppointmentFeedback.class);
                finish();
                startActivity(intent);
            }
        });

        eedit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, EditProfile.class);
                finish();
                startActivity(intent);
            }
        });

        emenstrual_tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,MenstrualTracker.class);
                finish();
                startActivity(intent);
            }
        });

        elogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(Dashboard.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });


    }
}
