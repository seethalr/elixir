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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    public static final String PHONENUMBER = "phonenumber";
    public static final String USERNAME = "username";
    public static final String PASSWORD="password";


    private EditText efirstname;
    private EditText elastname;
    private EditText eheight;
    private EditText edob;
    private EditText eweight;
    private Button eupdate;
    private Button eback;
    private String firstname;
    private String lastname;
    private String height;
    private String dob;
    private String weight;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;
    private String gen;

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
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String phonenum = preferences.getString(PHONENUMBER, "");
        final String name = preferences.getString(USERNAME, "");
        final String password=preferences.getString(PASSWORD, "");

        final String k  = "++91" + phonenum;
        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseRef=mDatabase.getReference();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstname=(String) dataSnapshot.child(k).child("ufirstname").getValue();
                lastname=(String) dataSnapshot.child(k).child("ulastname").getValue();
                height=(String) dataSnapshot.child(k).child("uheight").getValue();
                dob=(String) dataSnapshot.child(k).child("udob").getValue();
                weight=(String) dataSnapshot.child(k).child("uweight").getValue();
                efirstname.setText(firstname);
                elastname.setText(lastname);
                eheight.setText(height);
                eweight.setText(weight);
                edob.setText(dob);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseRef=mDatabase.getInstance().getReference();
        eupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditProfile.this, "Profile updated", Toast.LENGTH_SHORT);
                firstname=efirstname.getText().toString();
                lastname=elastname.getText().toString();
                height=eheight.getText().toString();
                weight=eweight.getText().toString();
                dob=edob.getText().toString();
                mDatabaseRef.child(k).child("ufirstname").setValue(firstname);
                mDatabaseRef.child(k).child("ulastname").setValue(lastname);
                mDatabaseRef.child(k).child("udob").setValue(dob);
                mDatabaseRef.child(k).child("uweight").setValue(weight);
                mDatabaseRef.child(k).child("uheight").setValue(height);
                Intent intent=new Intent(EditProfile.this, Dashboard.class);
                finish();
                startActivity(intent);
            }
        });

        eback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditProfile.this, Dashboard.class);
                finish();
                startActivity(intent);
            }
        });
    }
}