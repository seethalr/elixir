package com.seethalreghunath.elixir;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class MenstrualTracker extends AppCompatActivity {
    private int day;
    private int month;
    private Button egetnew;
    private Button eback;
    private TextView enewdate;
    private EditText hdate;
    private DatePickerDialog datePickerDialog;
    Integer d;
    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menstrual_tracker);

        egetnew = (Button) findViewById(R.id.get_new);
        eback = (Button) findViewById(R.id.back);
        enewdate = (TextView) findViewById(R.id.newdate);
        hdate = (EditText) findViewById(R.id.ldate);


        hdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MenstrualTracker.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                hdate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                day = dayOfMonth;
                                month = monthOfYear;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        egetnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdate(day, month);

            }
        });

        eback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenstrualTracker.this, Dashboard.class);
                finish();
                startActivity(intent);
            }
        });

    }

    private void setdate(int date, int m) {
        //     int mon_in = 0;
        String mon;
        ArrayList<String> months = new ArrayList<String>(Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"));
//        if(months.contains(mon)){
//            mon_in = months.indexOf(mon);
//        }
        Date d1 = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        int year = cal.get(Calendar.YEAR);
        Date d2 = new Date(year, m, date);
        Calendar c = Calendar.getInstance();
        c.setTime(d2);
        c.add(Calendar.DATE, 28);
        d1 = c.getTime();
        cal.setTime(d1);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        mon = months.get(month); //expected month
        date = day; //expected date
        enewdate.setText(date + " " + mon);

        Calendar current = Calendar.getInstance();

        Calendar calc = Calendar.getInstance();
        calc.set(2018, month, date, 20, 00, 00);

        if(calc.compareTo(current) <= 0){
            //The set Date/Time already passed
            Toast.makeText(getApplicationContext(),
                    "Invalid Date/Time",
                    Toast.LENGTH_LONG).show();
        }else{
            setAlarm(calc);
        }



    }

    private void setAlarm(Calendar targetCal){


        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

}

