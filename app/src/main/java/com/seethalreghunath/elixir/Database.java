package com.seethalreghunath.elixir;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database extends AppCompatActivity {

    public static final String PHONENUMBER = "phonenumber";

    private TextView t1;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;
    String snap1, snap2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdatabase);

        t1=(TextView) findViewById(R.id.details);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String phonenum = preferences.getString(PHONENUMBER, "");
        final String k  = "++91" + phonenum;

        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseRef=mDatabase.getReference();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                    //snap1 = (String) dataSnapshot.getValue();
                    snap2=(String) dataSnapshot.child(k).child("ufirstname").getValue();
                t1.setText(snap2);

                System.out.print(snap2);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
