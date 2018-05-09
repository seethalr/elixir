package com.seethalreghunath.elixir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Profile extends AppCompatActivity {

    public static final String PHONENUMBER = "phonenumber";
    public static final String USERNAME = "username";
    public static final String PASSWORD="password";

    private EditText efirstname;
    private EditText elastname;
    private EditText eheight;
    private EditText edob;
    private EditText eweight;
    private Button eupdate;
    private String name;
    private String phonenum;
    private String password;
    private String gen;
    private DatabaseReference mDatabaseReference;
    private Button eback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        efirstname=(EditText) findViewById(R.id.firstname);
        elastname=(EditText) findViewById(R.id.lastname);
        eheight=(EditText) findViewById(R.id.height);
        edob=(EditText) findViewById(R.id.dob);
        eweight=(EditText) findViewById(R.id.weight);
        eupdate=(Button) findViewById(R.id.update_button);
        eback=(Button) findViewById(R.id.back);

        mDatabaseReference=FirebaseDatabase.getInstance().getReference();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        name = preferences.getString(USERNAME, "");
        phonenum = preferences.getString(PHONENUMBER, "");
        phonenum = "++91" + phonenum;
        password=preferences.getString(PASSWORD, "");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gender= (String) parent.getItemAtPosition(position);
                gen=gender;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        eupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname=efirstname.getText().toString();
                String lastname=elastname.getText().toString();
                String dob=edob.getText().toString();
                String height=eheight.getText().toString();
                String weight=eweight.getText().toString();

                User newuser=new User(name, phonenum, password, firstname, lastname, height, weight, gen, dob);
                mDatabaseReference.child(newuser.uphno).setValue(newuser);
                Intent intent=new Intent(Profile.this, Dashboard.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
