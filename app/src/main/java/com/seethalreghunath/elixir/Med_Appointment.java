package com.seethalreghunath.elixir;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Med_Appointment extends AppCompatActivity {
    public static final String PHONENUMBER = "phonenumber";
    public static final String JVALUE= "jvalue";

    private EditText edoc_name;
    private EditText ehos_name;
    private EditText econsultdate;
    private DatePickerDialog datePickerDialog;
    private EditText econsulttime;
    private Button eadd;
    private DatabaseReference mDatabaseReference;
    private String finaltime;
    private String finaldate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_appointment);

        ehos_name=(EditText) findViewById(R.id.hname);
        edoc_name=(EditText) findViewById(R.id.dname);
        econsultdate=(EditText) findViewById(R.id.consult_date);
        econsulttime=(EditText) findViewById(R.id.consult_time);
        eadd=(Button) findViewById(R.id.addrecord);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String phonenum = preferences.getString(PHONENUMBER, "");
        final String num = "++91" + phonenum;
        SharedPreferences.Editor editor = preferences.edit();
        final int i=preferences.getInt(JVALUE, -1);
        editor.putInt(JVALUE, i+1);
        editor.apply();
        econsultdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Med_Appointment.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                econsultdate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        econsulttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Med_Appointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        econsulttime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        eadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorname=edoc_name.getText().toString();
                String hospitalname=ehos_name.getText().toString();
                mDatabaseReference.child(num).child("umedicalappointments").child("umedicalappointment"+(i+1)).child("doctorname").setValue(doctorname);
                mDatabaseReference.child(num).child("umedicalappointments").child("umedicalappointment"+(i+1)).child("hospitalname").setValue(hospitalname);
                Intent intent=new Intent(Med_Appointment.this, Dashboard.class);
                finish();
                startActivity(intent);
            }
        });
    }
}