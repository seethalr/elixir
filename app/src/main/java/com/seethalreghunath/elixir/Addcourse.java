package com.seethalreghunath.elixir;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Addcourse extends AppCompatActivity {

    public static final String PHONENUMBER = "phonenumber";
    public static final String MEDICINENAME= "medicinename";
    public static final String IVALUE= "ivalue";

    private EditText emedname;
    private EditText etotalcount;
    private EditText ecountperday;
    private EditText eduration;
    private EditText etime1;
    private EditText etime2;
    private EditText etime3;
    private EditText etime4;
    private Button esave;
    private String phonenum;
    private DatabaseReference mDatabaseReference;
    private Button eback;
    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;
    private int h1;
    private int m1;
    private int h2;
    private int m2;
    private int h3;
    private int m3;
    private int h4;
    private int m4;
    TimePicker myTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course);
        final ArrayList<Medicine> Emedicine= new ArrayList<Medicine>();

        emedname = (EditText) findViewById(R.id.med_name);
        etotalcount = (EditText) findViewById(R.id.tot_count);
        ecountperday=(EditText) findViewById(R.id.count_per_day);
        eduration = (EditText) findViewById(R.id.duration);
        etime1 = (EditText) findViewById(R.id.time1);
        etime2 = (EditText) findViewById(R.id.time2);
        etime3 = (EditText) findViewById(R.id.time3);
        etime4 = (EditText) findViewById(R.id.time4);
        esave = (Button) findViewById(R.id.save);
        eback=(Button) findViewById(R.id.back);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        eback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addcourse.this, MedicationCourse.class);
                finish();
                startActivity(intent);
            }
        });

        etime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Addcourse.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etime1.setText( selectedHour + ":" + selectedMinute);
                        h1=selectedHour;
                        m1=selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        etime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Addcourse.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etime1.setText( selectedHour + ":" + selectedMinute);
                        h2=selectedHour;
                        m2=selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        etime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Addcourse.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etime1.setText( selectedHour + ":" + selectedMinute);
                        h3=selectedHour;
                        m3=selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        etime4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Addcourse.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etime1.setText( selectedHour + ":" + selectedMinute);
                        h4=selectedHour;
                        m4=selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        phonenum = preferences.getString(PHONENUMBER, "");
        phonenum = "++91" + phonenum;
        String medicine_name = emedname.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MEDICINENAME, medicine_name);
        final int i=preferences.getInt(IVALUE, -1);
        editor.putInt(IVALUE, i+1);
        editor.apply();
        esave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medicine_name = emedname.getText().toString();
                String tcount = etotalcount.getText().toString();
                String ecpd = ecountperday.getText().toString();
                String ed = eduration.getText().toString();
                String et1 = etime1.getText().toString();
                String et2 = etime2.getText().toString();
                String et3 = etime3.getText().toString();
                String et4 = etime4.getText().toString();
                Medicine med = new Medicine(medicine_name, tcount, ecpd, ed, et1, et2, et3, et4);
                Emedicine.add(med);

                mDatabaseReference.child(phonenum).child("umedicationcourses").child("umedicationcourse"+(i+1)).setValue(med);
                Intent intent=new Intent(Addcourse.this, MedicationCourse.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
